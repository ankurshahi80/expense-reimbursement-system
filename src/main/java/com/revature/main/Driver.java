package com.revature.main;

import com.revature.utility.ConnectionUtility;

import java.sql.Connection;
import java.sql.SQLException;

public class Driver {
    public static void main(String[] args) {
        try {
            Connection con = ConnectionUtility.getConnection();
            System.out.println(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
