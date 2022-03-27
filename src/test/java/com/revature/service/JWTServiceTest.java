package com.revature.service;

import com.revature.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JWTServiceTest {
    @Test
    public void creates_jwtToken() {

        // Arrange
        User testUser = new User(1000,"test","test123","Thomas","Anderson","ta@gmail.com","manager");
        JWTService jwtService = new JWTService();

        // Act
        String actual = jwtService.createJwt(testUser);

        // Assert
        Assertions.assertNotNull(actual);
    }

    @Test
    public void validates_jwtToken() {

    }
}
