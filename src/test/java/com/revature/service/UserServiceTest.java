package com.revature.service;

import com.revature.dao.UserDao;
import com.revature.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.security.auth.login.FailedLoginException;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Test
    public void testLogin_positive() throws SQLException, FailedLoginException {
        // Arrange
        UserDao mockUserDao = mock(UserDao.class);
        User testUser = new User(1000,"test","test123","Thomas","Anderson","ta@gmail.com","manager");

        when(mockUserDao.getUser("test","test123")).thenReturn(testUser);

        UserService userService = new UserService(mockUserDao);

        // Act
        User actual = userService.login("test","test123");

        // Assert
        Assertions.assertEquals(testUser, actual);
    }

    @Test
    public void testLogin_negative(){
        // Arrange
        UserDao mockUserDao = mock(UserDao.class);
        UserService userService = new UserService(mockUserDao);

        // Act and Assert
        Assertions.assertThrows(FailedLoginException.class,()->{
            userService.login("test","test");
        });
    }
}
