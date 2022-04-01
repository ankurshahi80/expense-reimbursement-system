package com.revature.controller;

import com.revature.dto.loginDTO;
import com.revature.model.User;
import com.revature.service.JWTService;
import com.revature.service.UserService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class AuthenticationController implements Controller {

    private UserService userService;
    private JWTService jwtService;

    public AuthenticationController(){
        this.userService = new UserService();
        this.jwtService = new JWTService();
    }

    private Handler login = ctx -> {
        loginDTO loginInfo = ctx.bodyAsClass(loginDTO.class);

        User user = userService.login(loginInfo.getUsername(),loginInfo.getPassword());

        String jwt = this.jwtService.createJwt(user);

        ctx.header("Access-Control-Expose-Headers","*");
        ctx.header("token",jwt);
        ctx.json(user);
    };

    @Override
    public void mapEndPoints(Javalin app) {
        app.post("/login",login);
    }
}
