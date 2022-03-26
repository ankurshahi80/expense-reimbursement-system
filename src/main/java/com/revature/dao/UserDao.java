package com.revature.dao;

import com.revature.model.User;
import com.revature.utility.ConnectionUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    public User getUser(String username, String password) throws SQLException {
        try(Connection con = ConnectionUtility.getConnection()){
            String sql = "select " +
                    "eu.ers_users_id, " +
                    "eu.ers_username, " +
                    "eu.ers_password, " +
                    "eu.user_first_name, " +
                    "eu.user_last_name, " +
                    "eu.user_email, " +
                    "eur.user_role  " +
                    "from ers_users eu " +
                    "left join ers_user_roles eur " +
                    "on eu.user_role_id = eur.ers_user_role_id " +
                    "where eu.ers_username = ? and eu.ers_password = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,username);
            pstmt.setString(2,password);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                int id = rs.getInt("ers_users_id");
                String un = rs.getString("ers_username");
                String pw = rs.getString("ers_password");
                String firstName = rs.getString("user_first_name");
                String lastName = rs.getString("user_last_name");
                String email = rs.getString("user_email");
                String role = rs.getString("user_role");

                return new User(id, un, pw, firstName, lastName, email, role);
            }

            return null;
        }
    }
}
