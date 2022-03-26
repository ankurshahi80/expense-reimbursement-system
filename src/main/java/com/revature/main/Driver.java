package com.revature.main;

import com.revature.dao.UserDao;
import com.revature.utility.ConnectionUtility;

import java.sql.Connection;
import java.sql.SQLException;

public class Driver {
    public static void main(String[] args) {
        UserDao dao = new UserDao();

        try {
            System.out.println(dao.getUser("darthvader", "test123"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ;
    }
}
