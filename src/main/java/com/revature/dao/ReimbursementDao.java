package com.revature.dao;

import com.revature.dto.AddReimbursementDTO;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.utility.ConnectionUtility;

import java.io.InputStream;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReimbursementDao {

    public Reimbursement addReimbursement(int userId, AddReimbursementDTO dto) throws SQLException {
        try(Connection con = ConnectionUtility.getConnection()){
            String sql = "INSERT INTO ers_reimbursement " +
                    "(reimb_amount, reimb_submitted, reimb_description, " +
                    "reimb_receipt, reimb_author, reimb_type_id) " +
                    "VALUES (?,?,?,?,?,?)";

            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setDouble(1,dto.getReimbAmount());
            pstmt.setTimestamp(2, Timestamp.from(Instant.now()));
            pstmt.setString(3, dto.getReimbDescription());
            pstmt.setBinaryStream(4, dto.getReimbReceipt());
            pstmt.setInt(5,userId);
            pstmt.setInt(6,dto.getReimbType());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();

            rs.next();
            int reimbId = rs.getInt(1);

            String sql2 = "SELECT * FROM ers_users WHERE ers_users_id= ?";
            PreparedStatement pstmt2 = con.prepareStatement(sql2);
            pstmt2.setInt(1,userId);

            ResultSet rs2 = pstmt2.executeQuery();
            rs2.next();

            int reimbAuthorId = rs2.getInt("ers_user_id");
            String reimbAuthorName = rs2.getString("emp_eu.ers_username");
            String reimbAuthorPassword = rs2.getString("emp_eu.ers_password");
            String reimbAuthorFirstName = rs2.getString("emp_eu.user_first_name");
            String reimbAuthorLastName = rs2.getString("emp_eu.user_last_name");
            String reimbAuthorEmail = rs2.getString("emp_eu.user_email");
            String reimbAuthorRole = "Employee";

            Timestamp reimbSubmitted = rs2.getTimestamp("reimb_submitted");
            Timestamp reimbResolved = rs2.getTimestamp("reimb_resolved");



            User reimbAuthor = new User(reimbAuthorId, reimbAuthorName, reimbAuthorPassword, reimbAuthorFirstName, reimbAuthorLastName, reimbAuthorEmail, reimbAuthorRole);

            Reimbursement reimbursement = new Reimbursement(reimbId, dto.getReimbAmount(),reimbSubmitted, reimbResolved,dto.getReimbDescription(),dto.getReimbReceipt(),reimbAuthor,null,"Pending","To be Coded");

            return reimbursement;
        }

    }

    public List<Reimbursement> getAllReimbursements() throws SQLException {
        try(Connection con = ConnectionUtility.getConnection()) {
            List<Reimbursement> reimbursements = new ArrayList<>();

            String sql = "SELECT * " +
                    "FROM ers_reimbursement er " +
                    "LEFT JOIN ers_users emp_eu " +
                    "ON emp_eu.ers_user_id = er.reimb_author " +
                    "LEFT JOIN ers_users mgr_eu " +
                    "ON mgr_eu.ers_user_id = er.reimb_resolver " +
                    "LEFT JOIN ers_reimbursement_status ers " +
                    "ON ers.reimb_status_id = er.reimb_status_id " +
                    "LEFT JOIN ers_reimbursement_type ert " +
                    "ON ert.reimb_type_id = er.reimb_type_id";

            PreparedStatement ptsmt = con.prepareStatement(sql);

            ResultSet rs = ptsmt.executeQuery();

            while (rs.next()) {
                // Reimbursement
                int reimbId = rs.getInt("reimb_id");
                double reimbAmt = rs.getDouble("reimb_amount");
                Timestamp reimbSubmitted = rs.getTimestamp("reimb_submitted");
                Timestamp reimbResolved = rs.getTimestamp("reimb_resolved");
                String reimbDescription = rs.getString("reimb_description");
                InputStream reimbReceipt = rs.getBinaryStream("reimb_receipt");

                // Author
                int reimbAuthorId = rs.getInt("reimb_author");
                String reimbAuthorName = rs.getString("emp_eu.ers_username");
                String reimbAuthorPassword = rs.getString("emp_eu.ers_password");
                String reimbAuthorFirstName = rs.getString("emp_eu.user_first_name");
                String reimbAuthorLastName = rs.getString("emp_eu.user_last_name");
                String reimbAuthorEmail = rs.getString("emp_eu.user_email");
                String reimbAuthorRole = "Employee";

                User reimbAuthor = new User(reimbAuthorId, reimbAuthorName, reimbAuthorPassword, reimbAuthorFirstName, reimbAuthorLastName, reimbAuthorEmail, reimbAuthorRole);

                // Resolver
                int reimbResolverId = rs.getInt("reimb_resolver");
                String reimbResolverName = rs.getString("mrg_eu.ers_username");
                String reimbResolverPassword = rs.getString("mgr_eu.ers_password");
                String reimbResolverFirstName = rs.getString("mgr_eu.user_first_name");
                String reimbResolverLastName = rs.getString("mgr_eu.user_last_name");
                String reimbResolverEmail = rs.getString("mgr_eu.user_email");
                String reimbResolverRole = "Manager";

                User reimbResolver = new User(reimbResolverId, reimbResolverName, reimbResolverPassword, reimbResolverFirstName, reimbResolverLastName, reimbResolverEmail, reimbResolverRole);

                String reimbStatus = rs.getString("ers.reimb_status");
                String reimbType = rs.getString("ert.reimb_type");

                Reimbursement r = new Reimbursement(reimbId, reimbAmt, reimbSubmitted, reimbResolved, reimbDescription,
                        reimbReceipt, reimbAuthor, reimbResolver, reimbStatus, reimbType);

                reimbursements.add(r);
            }

            return reimbursements;
        }
    }
}
