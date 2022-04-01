package com.revature.controller;

import com.revature.dto.AddReimbursementDTO;
import com.revature.dto.ResponseReimbursementDTO;
import com.revature.model.Reimbursement;
import com.revature.service.JWTService;
import com.revature.service.ReimbursementService;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.http.UploadedFile;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.apache.tika.Tika;

import java.io.InputStream;
import java.util.List;

public class ReimbursementController implements Controller{

    private JWTService jwtService;
    private ReimbursementService reimbursementService;

    public ReimbursementController() {
        this.jwtService = new JWTService();
        this.reimbursementService = new ReimbursementService();
    }
    // accessible to managers only
    private Handler getAllReimbursements = ctx -> {
        String jwt = ctx.header("Authorization").split(" ")[1];
        Jws<Claims> token = this.jwtService.parseJwt(jwt);

        if(!token.getBody().get("user_role").equals("Manager")) {
            throw new UnauthorizedResponse("You must be a manager to access this page.");
        }

        List<ResponseReimbursementDTO> reimbursements = this.reimbursementService.getAllReimbursements();
        ctx.json(reimbursements);

    };

    private Handler addReimbursement = ctx -> {
        String jwt = ctx.header("Authorization").split(" ")[1];
        Jws<Claims> token = this.jwtService.parseJwt(jwt);

        if(!token.getBody().get("user_role").equals("Employee")){
            throw new UnauthorizedResponse("You must be an employee to access this page.");
        }

        String userId = ctx.pathParam("user_id");
        int id = Integer.parseInt(userId);
        if(!token.getBody().get("user_id").equals(id)){
            throw new UnauthorizedResponse("You cannot submit reimbursements for other employees.");
        }
        AddReimbursementDTO dto = new AddReimbursementDTO();

        // capture reimbursement amount from the form parameters
        double reimbAmount = Double.parseDouble(ctx.formParam("reimbAmount"));
        dto.setReimbAmount(reimbAmount);

        // capture reimbursement description from the form parameter
        String reimbDescription = ctx.formParam("reimbDescription");
        dto.setReimbDescription(reimbDescription);

        // capture reimbursement receipt (image) from the form parameter and detect the file type (MIME type)
        UploadedFile file = ctx.uploadedFile("reimbReceipt");
        InputStream is = file.getContent();
        dto.setReimbReceipt(is);

        // capture reimbursement type
        int reimbType = Integer.parseInt(ctx.formParam("reimbType"));
        dto.setReimbType(reimbType);

        ResponseReimbursementDTO getDTO = this.reimbursementService.addReimbursement(id,dto);
//        List<Reimbursement> updatedList = this.reimbursementService.addReimbursement(id, dto);

        ctx.status(201);
        ctx.json(getDTO);
//        ctx.json(updatedList);
    };

    private Handler reviewReimbursement = ctx -> {
        String jwt = ctx.header("Authorization").split(" ")[1];
        Jws<Claims> token = this.jwtService.parseJwt(jwt);

        if(!token.getBody().get("user_role").equals("Manager")){
            throw new UnauthorizedResponse("You must be logged in a trainer");
        }

        String reimbId = ctx.pathParam("reimbursement_id");
        String reimbStatusString =ctx.body().toLowerCase();

        int reimbStatusInt = 0;
        switch (reimbStatusString) {
            case "approved" : reimbStatusInt = 2;
                                break;
            case "denied": reimbStatusInt = 3;
                            break;
        }

        int reimbResolverId = token.getBody().get("user_id", Integer.class);

        ResponseReimbursementDTO reimbursement = this.reimbursementService.reviewReimbursement(reimbId,reimbStatusInt,reimbResolverId);
        ctx.json(reimbursement);
    };

    private Handler getAllReimbursementsById = ctx ->{
        String jwt = ctx.header("Authorization").split(" ")[1];
        Jws<Claims> token = this.jwtService.parseJwt(jwt);

        if(!token.getBody().get("user_role").equals("Employee")){
            throw new UnauthorizedResponse("You must be an employee to access this page.");
        }

        String userId = ctx.pathParam("user_id");
        int id = Integer.parseInt(userId);
        if(!token.getBody().get("user_id").equals(id)){
            throw new UnauthorizedResponse("You cannot view reimbursements for other employees.");
        }

        List<ResponseReimbursementDTO> reimbursementDTOs = this.reimbursementService.getAllReimbursementsById(id);
        ctx.json(reimbursementDTOs);
    };

    private Handler getReimbursementImage = ctx -> {

        String reimbursementId = ctx.pathParam("reimbursement_id");


        InputStream reimbReceipt = this.reimbursementService.getReimbursementImage(reimbursementId);
        Tika tika = new Tika();
        String mimeType = tika.detect(reimbReceipt);

        ctx.header("Content-type", mimeType);
        ctx.result(reimbReceipt);
    };

    @Override
    public void mapEndPoints(Javalin app) {
        app.get("/reimbursements", getAllReimbursements);
        app.get("/users/{user_id}/reimbursements", getAllReimbursementsById);
        app.post("/users/{user_id}/reimbursements", addReimbursement);
        app.get("/reimbursements/{reimbursement_id}/image",getReimbursementImage);
        app.patch("/reimbursements/{reimbursement_id}", reviewReimbursement);
    }
}
