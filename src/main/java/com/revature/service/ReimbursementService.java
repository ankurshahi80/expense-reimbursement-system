package com.revature.service;

import com.revature.dao.ReimbursementDao;
import com.revature.dto.AddReimbursementDTO;
import com.revature.dto.ResponseReimbursementDTO;
import com.revature.model.Reimbursement;

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

    public ResponseReimbursementDTO addReimbursement(int reimbAuthorId, AddReimbursementDTO dto) throws SQLException {
        Reimbursement reimbursementAdded = this.reimbursementDao.addReimbursement(reimbAuthorId, dto);

        return new ResponseReimbursementDTO(reimbursementAdded.getReimbId(), reimbursementAdded.getReimbAmt(),reimbursementAdded.getReimbSubmitted(),
                reimbursementAdded.getReimbDescription(), reimbursementAdded.getReimbReceipt(),reimbursementAdded.getReimbAuthor().getUsername(),
                reimbursementAdded.getReimbResolver().getUsername(),
                reimbursementAdded.getReimbStatus(),reimbursementAdded.getReimbType());
    }

    public List<ResponseReimbursementDTO> getAllReimbursements() throws SQLException {
        List<Reimbursement> reimbursements = this.reimbursementDao.getAllReimbursements();

        List<ResponseReimbursementDTO> reimbursementDTOs = new ArrayList<>();

        for (Reimbursement reimbursement:
             reimbursements) {
            reimbursementDTOs.add(new ResponseReimbursementDTO(reimbursement.getReimbId(), reimbursement.getReimbAmt(),
                    reimbursement.getReimbSubmitted(),reimbursement.getReimbDescription(),
                    reimbursement.getReimbReceipt(),reimbursement.getReimbAuthor().getUsername(),
                    reimbursement.getReimbResolver().getUsername(),reimbursement.getReimbStatus(),
                    reimbursement.getReimbType()));
        }

        return reimbursementDTOs;
    }
}
