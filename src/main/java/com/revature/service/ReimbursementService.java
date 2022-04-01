package com.revature.service;

import com.revature.dao.ReimbursementDao;
import com.revature.dto.AddReimbursementDTO;
import com.revature.dto.ResponseReimbursementDTO;
import com.revature.exception.ImageNotFoundException;
import com.revature.exception.InvalidImageException;
import com.revature.model.Reimbursement;
import org.apache.tika.Tika;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementService {

    private ReimbursementDao reimbursementDao;

    public ReimbursementService() {
        this.reimbursementDao = new ReimbursementDao();
    }

    public ReimbursementService(ReimbursementDao mockDao) {
        this.reimbursementDao = mockDao;
    }

    public ResponseReimbursementDTO reviewReimbursement(String reimbId, int reimbStatus, int reimbResolverId) throws SQLException {
        try{
            int intReimbId = Integer.parseInt(reimbId);

            Reimbursement reimbursement = this.reimbursementDao.reviewReimbursement(intReimbId,reimbResolverId,reimbStatus);
            return new ResponseReimbursementDTO(reimbursement.getReimbId(),
                    reimbursement.getReimbAmt(), reimbursement.getReimbSubmitted(), reimbursement.getReimbResolved(), reimbursement.getReimbDescription(),
                     reimbursement.getReimbAuthor().getUsername(),
                    reimbursement.getReimbResolver().getUsername(),
                    reimbursement.getReimbStatus(),reimbursement.getReimbType());

        } catch(NumberFormatException e){
            throw new IllegalArgumentException("Please check the reimbursement id.");
        }
    }

    public ResponseReimbursementDTO addReimbursement(int reimbAuthorId, AddReimbursementDTO dto) throws SQLException, InvalidImageException, IOException {

        Tika tika = new Tika();
        String mimeType = tika.detect(dto.getReimbReceipt());

        if(!mimeType.equals("image/jpeg") && !mimeType.equals("image/png") && !mimeType.equals("image/gif")){
            throw new InvalidImageException("Image must be a JPEG, PNG, or GIF");
        }
        Reimbursement reimbursement = this.reimbursementDao.addReimbursement(reimbAuthorId,dto);
        return new ResponseReimbursementDTO(reimbursement.getReimbId(),
                reimbursement.getReimbAmt(), reimbursement.getReimbSubmitted(), reimbursement.getReimbResolved(), reimbursement.getReimbDescription(),
                reimbursement.getReimbAuthor().getUsername(),
                reimbursement.getReimbResolver().getUsername(),
                reimbursement.getReimbStatus(),reimbursement.getReimbType());
    }

    public List<ResponseReimbursementDTO> getAllReimbursements() throws SQLException {
        List<Reimbursement> reimbursements = this.reimbursementDao.getAllReimbursements();

        List<ResponseReimbursementDTO> reimbursementDTOs = new ArrayList<>();

        for (Reimbursement reimbursement:
             reimbursements) {
            reimbursementDTOs.add(new ResponseReimbursementDTO(reimbursement.getReimbId(), reimbursement.getReimbAmt(),
                    reimbursement.getReimbSubmitted(), reimbursement.getReimbResolved(), reimbursement.getReimbDescription(),
                    reimbursement.getReimbAuthor().getUsername(),
                    reimbursement.getReimbResolver().getUsername(),reimbursement.getReimbStatus(),
                    reimbursement.getReimbType()));
        }

        return reimbursementDTOs;
    }

    public List<ResponseReimbursementDTO> getAllReimbursementsById(int empId) throws SQLException {

        List<Reimbursement> reimbursements = this.reimbursementDao.getAllReimbursementsById(empId);

        List<ResponseReimbursementDTO> reimbursementDTOs = new ArrayList<>();

        for (Reimbursement reimbursement:
                reimbursements) {
            reimbursementDTOs.add(new ResponseReimbursementDTO(reimbursement.getReimbId(), reimbursement.getReimbAmt(),
                    reimbursement.getReimbSubmitted(),reimbursement.getReimbResolved(), reimbursement.getReimbDescription(),
                    reimbursement.getReimbAuthor().getUsername(),
                    reimbursement.getReimbResolver().getUsername(),reimbursement.getReimbStatus(),
                    reimbursement.getReimbType()));
        }

        return reimbursementDTOs;
    }

    public InputStream getReimbursementImage(String reimbId, String empId) throws SQLException, ImageNotFoundException {
        try{
            int rId = Integer.parseInt(reimbId);
            int eId = Integer.parseInt(empId);
            InputStream is = this.reimbursementDao.getReimbursementImage(rId, eId);

            if(is == null){
                throw new ImageNotFoundException("Reimbursement id " + reimbId + " does not have an imagge");
            }

            return is;
        } catch(NumberFormatException e){
            throw new IllegalArgumentException("Reimburesement id and emp id must be an int value");
        }



    }
}
