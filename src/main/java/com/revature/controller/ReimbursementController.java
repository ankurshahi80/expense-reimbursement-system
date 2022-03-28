package com.revature.controller;

import com.revature.dto.ResponseReimbursementDTO;
import com.revature.service.JWTService;
import com.revature.service.ReimbursementService;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

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
    };

    @Override
    public void mapEndPoints(Javalin app) {
        app.get("/reimbursements", getAllReimbursements);
        app.get("/addReimbursement", addReimbursement);
    }
}
