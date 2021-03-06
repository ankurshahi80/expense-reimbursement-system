package com.revature.dao;

import com.revature.dto.AddReimbursementDTO;
import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.utility.ConnectionUtility;
import jdk.nashorn.internal.ir.Assignment;

import java.io.InputStream;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReimbursementDao {

    public Reimbursement reviewReimbursement(int reimbId, int reimResolver, int reimbStatus ) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) {
            con.setAutoCommit(false);

            String sql = "UPDATE ers_reimbursement " +
                    "SET reimb_status_id = ?, " +
                    "reimb_resolver = ?, " +
                    "reimb_resolved = ? " +
                    "WHERE reimb_id = ? ";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, reimbStatus);
            pstmt.setInt(2, reimResolver);
            pstmt.setTimestamp(3, Timestamp.from(Instant.now()));
            pstmt.setInt(4, reimbId);

            int status = pstmt.executeUpdate();

            String sql2 = "select er.reimb_id as reimbursement_id, " +
                    "er.reimb_amount as reimbursement_amount, " +
                    "er.reimb_submitted as date_submitted, " +
                    "er.reimb_resolved as date_resolved, " +
                    "er.reimb_description as reimbursement_description, " +
                    "er.reimb_receipt as reimbursement_receipt, " +
                    "er.reimb_author as employee_id, " +
                    "emp_eu.ers_username as employee_username, " +
                    "emp_eu.ers_password as employee_password, " +
                    "emp_eu.user_first_name as employee_first_name, " +
                    "emp_eu.user_last_name as employee_last_name, " +
                    "emp_eu.user_email as employee_email, " +
                    "er.reimb_resolver as manager_id, " +
                    "mgr_eu.ers_username as manager_username, " +
                    "mgr_eu.ers_password as manager_password, " +
                    "mgr_eu.user_first_name as manager_first_name, " +
                    "mgr_eu.user_last_name as manager_last_name, " +
                    "mgr_eu.user_email as manager_email, " +
                    "ers.reimb_status as reimbursement_status, " +
                    "ert.reimb_type as reimbursement_type " +
                    "FROM ers_reimbursement er " +
                    "LEFT JOIN ers_users emp_eu " +
                    "ON emp_eu.ers_users_id = er.reimb_author " +
                    "LEFT JOIN ers_users mgr_eu " +
                    "ON mgr_eu.ers_users_id = er.reimb_resolver " +
                    "LEFT JOIN ers_reimbursement_status ers " +
                    "ON ers.reimb_status_id = er.reimb_status_id " +
                    "LEFT JOIN ers_reimbursement_type ert " +
                    "ON ert.reimb_type_id = er.reimb_type_id " +
                    "WHERE er.reimb_id = ?";

            PreparedStatement pstmt2 = con.prepareStatement(sql2);
            pstmt2.setInt(1, reimbId);

            ResultSet rs = pstmt2.executeQuery();

            rs.next();
            // Reimbursement
            int rId = rs.getInt("reimbursement_id");
            double reimbAmt = rs.getDouble("reimbursement_amount");
            Timestamp reimbSubmitted = rs.getTimestamp("date_submitted");
            Timestamp reimbResolved = rs.getTimestamp("date_resolved");
            String reimbDescription = rs.getString("reimbursement_description");
            InputStream reimbReceipt = rs.getBinaryStream("reimbursement_receipt");

            // Author
            int reimbAuthorId = rs.getInt("employee_id");
            String reimbAuthorName = rs.getString("employee_username");
            String reimbAuthorPassword = rs.getString("employee_password");
            String reimbAuthorFirstName = rs.getString("employee_first_name");
            String reimbAuthorLastName = rs.getString("employee_last_name");
            String reimbAuthorEmail = rs.getString("employee_email");
            String reimbAuthorRole = "Employee";

            User reimbAuthor = new User(reimbAuthorId, reimbAuthorName, reimbAuthorPassword, reimbAuthorFirstName, reimbAuthorLastName, reimbAuthorEmail, reimbAuthorRole);

            // Resolver
            int reimbResolverId = rs.getInt("manager_id");
            String reimbResolverName = rs.getString("manager_username");
            String reimbResolverPassword = rs.getString("manager_password");
            String reimbResolverFirstName = rs.getString("manager_first_name");
            String reimbResolverLastName = rs.getString("manager_last_name");
            String reimbResolverEmail = rs.getString("manager_email");
            String reimbResolverRole = "Manager";

            User reimbResolver = new User(reimbResolverId, reimbResolverName, reimbResolverPassword, reimbResolverFirstName, reimbResolverLastName, reimbResolverEmail, reimbResolverRole);

            String newReimbStatus = rs.getString("reimbursement_status");
            String reimbType = rs.getString("reimbursement_type");

            Reimbursement r = new Reimbursement(rId, reimbAmt, reimbSubmitted, reimbResolved, reimbDescription,
                    reimbReceipt, reimbAuthor, reimbResolver, newReimbStatus, reimbType);


            con.commit();
            return r;

        }
    }

    public Reimbursement addReimbursement(int userId, AddReimbursementDTO dto) throws SQLException {
        try(Connection con = ConnectionUtility.getConnection()){
//            con.setAutoCommit(false);

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

            Reimbursement reimbursement =  getReimbursementsById(reimbId);

//            con.commit();

            return reimbursement;
        }

    }

    public List<Reimbursement> getAllReimbursements() throws SQLException {
        try(Connection con = ConnectionUtility.getConnection()) {
            List<Reimbursement> reimbursements = new ArrayList<>();

            String sql = "select er.reimb_id as reimbursement_id, " +
                    "er.reimb_amount as reimbursement_amount, " +
                    "er.reimb_submitted as date_submitted, " +
                    "er.reimb_resolved as date_resolved, " +
                    "er.reimb_description as reimbursement_description, " +
                    "er.reimb_receipt as reimbursement_receipt, " +
                    "er.reimb_author as employee_id, " +
                    "emp_eu.ers_username as employee_username, " +
                    "emp_eu.ers_password as employee_password, " +
                    "emp_eu.user_first_name as employee_first_name, " +
                    "emp_eu.user_last_name as employee_last_name, " +
                    "emp_eu.user_email as employee_email, " +
                    "er.reimb_resolver as manager_id, " +
                    "mgr_eu.ers_username as manager_username, " +
                    "mgr_eu.ers_password as manager_password, "+
                    "mgr_eu.user_first_name as manager_first_name, "+
                    "mgr_eu.user_last_name as manager_last_name, "+
                    "mgr_eu.user_email as manager_email, " +
                    "ers.reimb_status as reimbursement_status, "+
                    "ert.reimb_type as reimbursement_type "+
                    "FROM ers_reimbursement er "+
                    "LEFT JOIN ers_users emp_eu "+
                    "ON emp_eu.ers_users_id = er.reimb_author "+
                    "LEFT JOIN ers_users mgr_eu "+
                    "ON mgr_eu.ers_users_id = er.reimb_resolver "+
                    "LEFT JOIN ers_reimbursement_status ers "+
                    "ON ers.reimb_status_id = er.reimb_status_id "+
                    "LEFT JOIN ers_reimbursement_type ert " +
                    "ON ert.reimb_type_id = er.reimb_type_id " +
                    " ORDER BY er.reimb_id";

            PreparedStatement ptsmt = con.prepareStatement(sql);

            ResultSet rs = ptsmt.executeQuery();

            while (rs.next()) {
                // Reimbursement
                int reimbId = rs.getInt("reimbursement_id");
                double reimbAmt = rs.getDouble("reimbursement_amount");
                Timestamp reimbSubmitted = rs.getTimestamp("date_submitted");
                Timestamp reimbResolved = rs.getTimestamp("date_resolved");
                String reimbDescription = rs.getString("reimbursement_description");
                InputStream reimbReceipt = rs.getBinaryStream("reimbursement_receipt");
                System.out.println(reimbReceipt);

                // Author
                int reimbAuthorId = rs.getInt("employee_id");
                String reimbAuthorName = rs.getString("employee_username");
                String reimbAuthorPassword = rs.getString("employee_password");
                String reimbAuthorFirstName = rs.getString("employee_first_name");
                String reimbAuthorLastName = rs.getString("employee_last_name");
                String reimbAuthorEmail = rs.getString("employee_email");
                String reimbAuthorRole = "Employee";

                User reimbAuthor = new User(reimbAuthorId, reimbAuthorName, reimbAuthorPassword, reimbAuthorFirstName, reimbAuthorLastName, reimbAuthorEmail, reimbAuthorRole);

                // Resolver
                int reimbResolverId = rs.getInt("manager_id");
                String reimbResolverName = rs.getString("manager_username");
                String reimbResolverPassword = rs.getString("manager_password");
                String reimbResolverFirstName = rs.getString("manager_first_name");
                String reimbResolverLastName = rs.getString("manager_last_name");
                String reimbResolverEmail = rs.getString("manager_email");
                String reimbResolverRole = "Manager";

                User reimbResolver = new User(reimbResolverId, reimbResolverName, reimbResolverPassword, reimbResolverFirstName, reimbResolverLastName, reimbResolverEmail, reimbResolverRole);

                String reimbStatus = rs.getString("reimbursement_status");
                String reimbType = rs.getString("reimbursement_type");

                Reimbursement r = new Reimbursement(reimbId, reimbAmt, reimbSubmitted, reimbResolved, reimbDescription,
                        reimbReceipt, reimbAuthor, reimbResolver, reimbStatus, reimbType);

                reimbursements.add(r);
            }

            return reimbursements;
        }
    }

    public List<Reimbursement> getAllReimbursementsById(int empId) throws SQLException {
        try(Connection con = ConnectionUtility.getConnection()) {
            List<Reimbursement> reimbursements = new ArrayList<>();

            String sql = "select er.reimb_id as reimbursement_id, " +
                    "er.reimb_amount as reimbursement_amount, " +
                    "er.reimb_submitted as date_submitted, " +
                    "er.reimb_resolved as date_resolved, " +
                    "er.reimb_description as reimbursement_description, " +
                    "er.reimb_receipt as reimbursement_receipt, " +
                    "er.reimb_author as employee_id, " +
                    "emp_eu.ers_username as employee_username, " +
                    "emp_eu.ers_password as employee_password, " +
                    "emp_eu.user_first_name as employee_first_name, " +
                    "emp_eu.user_last_name as employee_last_name, " +
                    "emp_eu.user_email as employee_email, " +
                    "er.reimb_resolver as manager_id, " +
                    "mgr_eu.ers_username as manager_username, " +
                    "mgr_eu.ers_password as manager_password, "+
                    "mgr_eu.user_first_name as manager_first_name, "+
                    "mgr_eu.user_last_name as manager_last_name, "+
                    "mgr_eu.user_email as manager_email, " +
                    "ers.reimb_status as reimbursement_status, "+
                    "ert.reimb_type as reimbursement_type "+
                    "FROM ers_reimbursement er "+
                    "LEFT JOIN ers_users emp_eu "+
                    "ON emp_eu.ers_users_id = er.reimb_author "+
                    "LEFT JOIN ers_users mgr_eu "+
                    "ON mgr_eu.ers_users_id = er.reimb_resolver "+
                    "LEFT JOIN ers_reimbursement_status ers "+
                    "ON ers.reimb_status_id = er.reimb_status_id "+
                    "LEFT JOIN ers_reimbursement_type ert " +
                    "ON ert.reimb_type_id = er.reimb_type_id " +
                    "WHERE er.reimb_author = ?";

            PreparedStatement ptsmt = con.prepareStatement(sql);
            ptsmt.setInt(1, empId);

            ResultSet rs = ptsmt.executeQuery();

            while (rs.next()) {
                // Reimbursement
                int reimbId = rs.getInt("reimbursement_id");
                double reimbAmt = rs.getDouble("reimbursement_amount");
                Timestamp reimbSubmitted = rs.getTimestamp("date_submitted");
                Timestamp reimbResolved = rs.getTimestamp("date_resolved");
                String reimbDescription = rs.getString("reimbursement_description");
                InputStream reimbReceipt = rs.getBinaryStream("reimbursement_receipt");

                // Author
                int reimbAuthorId = rs.getInt("employee_id");
                String reimbAuthorName = rs.getString("employee_username");
                String reimbAuthorPassword = rs.getString("employee_password");
                String reimbAuthorFirstName = rs.getString("employee_first_name");
                String reimbAuthorLastName = rs.getString("employee_last_name");
                String reimbAuthorEmail = rs.getString("employee_email");
                String reimbAuthorRole = "Employee";

                User reimbAuthor = new User(reimbAuthorId, reimbAuthorName, reimbAuthorPassword, reimbAuthorFirstName, reimbAuthorLastName, reimbAuthorEmail, reimbAuthorRole);

                // Resolver
                int reimbResolverId = rs.getInt("manager_id");
                String reimbResolverName = rs.getString("manager_username");
                String reimbResolverPassword = rs.getString("manager_password");
                String reimbResolverFirstName = rs.getString("manager_first_name");
                String reimbResolverLastName = rs.getString("manager_last_name");
                String reimbResolverEmail = rs.getString("manager_email");
                String reimbResolverRole = "Manager";

                User reimbResolver = new User(reimbResolverId, reimbResolverName, reimbResolverPassword, reimbResolverFirstName, reimbResolverLastName, reimbResolverEmail, reimbResolverRole);

                String reimbStatus = rs.getString("reimbursement_status");
                String reimbType = rs.getString("reimbursement_type");

                Reimbursement r = new Reimbursement(reimbId, reimbAmt, reimbSubmitted, reimbResolved, reimbDescription,
                        reimbReceipt, reimbAuthor, reimbResolver, reimbStatus, reimbType);

                reimbursements.add(r);
            }

            return reimbursements;
        }
    }

    public Reimbursement getReimbursementsById(int reimbId) throws SQLException {
        try(Connection con = ConnectionUtility.getConnection()) {

            String sql = "select er.reimb_id as reimbursement_id, " +
                    "er.reimb_amount as reimbursement_amount, " +
                    "er.reimb_submitted as date_submitted, " +
                    "er.reimb_resolved as date_resolved, " +
                    "er.reimb_description as reimbursement_description, " +
                    "er.reimb_receipt as reimbursement_receipt, " +
                    "er.reimb_author as employee_id, " +
                    "emp_eu.ers_username as employee_username, " +
                    "emp_eu.ers_password as employee_password, " +
                    "emp_eu.user_first_name as employee_first_name, " +
                    "emp_eu.user_last_name as employee_last_name, " +
                    "emp_eu.user_email as employee_email, " +
                    "er.reimb_resolver as manager_id, " +
                    "mgr_eu.ers_username as manager_username, " +
                    "mgr_eu.ers_password as manager_password, " +
                    "mgr_eu.user_first_name as manager_first_name, " +
                    "mgr_eu.user_last_name as manager_last_name, " +
                    "mgr_eu.user_email as manager_email, " +
                    "ers.reimb_status as reimbursement_status, " +
                    "ert.reimb_type as reimbursement_type " +
                    "FROM ers_reimbursement er " +
                    "LEFT JOIN ers_users emp_eu " +
                    "ON emp_eu.ers_users_id = er.reimb_author " +
                    "LEFT JOIN ers_users mgr_eu " +
                    "ON mgr_eu.ers_users_id = er.reimb_resolver " +
                    "LEFT JOIN ers_reimbursement_status ers " +
                    "ON ers.reimb_status_id = er.reimb_status_id " +
                    "LEFT JOIN ers_reimbursement_type ert " +
                    "ON ert.reimb_type_id = er.reimb_type_id " +
                    "WHERE er.reimb_id = ?";

            PreparedStatement ptsmt = con.prepareStatement(sql);
            ptsmt.setInt(1, reimbId);


            ResultSet rs = ptsmt.executeQuery();
            rs.next();

            // Reimbursement

            double reimbAmt = rs.getDouble("reimbursement_amount");
            Timestamp reimbSubmitted = rs.getTimestamp("date_submitted");
            Timestamp reimbResolved = rs.getTimestamp("date_resolved");
            String reimbDescription = rs.getString("reimbursement_description");
            InputStream reimbReceipt = rs.getBinaryStream("reimbursement_receipt");

            // Author
            int reimbAuthorId = rs.getInt("employee_id");
            String reimbAuthorName = rs.getString("employee_username");
            String reimbAuthorPassword = rs.getString("employee_password");
            String reimbAuthorFirstName = rs.getString("employee_first_name");
            String reimbAuthorLastName = rs.getString("employee_last_name");
            String reimbAuthorEmail = rs.getString("employee_email");
            String reimbAuthorRole = "Employee";

            User reimbAuthor = new User(reimbAuthorId, reimbAuthorName, reimbAuthorPassword, reimbAuthorFirstName, reimbAuthorLastName, reimbAuthorEmail, reimbAuthorRole);

            // Resolver
            int reimbResolverId = rs.getInt("manager_id");
            String reimbResolverName = rs.getString("manager_username");
            String reimbResolverPassword = rs.getString("manager_password");
            String reimbResolverFirstName = rs.getString("manager_first_name");
            String reimbResolverLastName = rs.getString("manager_last_name");
            String reimbResolverEmail = rs.getString("manager_email");
            String reimbResolverRole = "Manager";

            User reimbResolver = new User(reimbResolverId, reimbResolverName, reimbResolverPassword, reimbResolverFirstName, reimbResolverLastName, reimbResolverEmail, reimbResolverRole);

            String reimbStatus = rs.getString("reimbursement_status");
            String reimbType = rs.getString("reimbursement_type");

            Reimbursement r = new Reimbursement(reimbId, reimbAmt, reimbSubmitted, reimbResolved, reimbDescription,
                    reimbReceipt, reimbAuthor, reimbResolver, reimbStatus, reimbType);

            return r;
        }
    }

    public InputStream getReimbursementImage(int reimbId) throws SQLException {
        try(Connection con = ConnectionUtility.getConnection()){
            String sql = "SELECT er.reimb_receipt " +
                    " FROM ers_reimbursement er " +
                    "WHERE er.reimb_id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1,reimbId);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                InputStream is = rs.getBinaryStream("reimb_receipt");
                return is;
            } else {
                return null;
            }
        }
    };
}
