package com.revature.controller;

import com.revature.dto.loginDTO;
import com.revature.model.User;
import com.revature.service.UserService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class AuthenticationController implements Controller {

    private UserService userService;

    public AuthenticationController(){
        this.userService = new UserService();
    }

    private Handler login = ctx -> {
        loginDTO loginInfo = ctx.bodyAsClass(loginDTO.class);

        User user = userService.login(loginInfo.getUsername(),loginInfo.getPassword());
        ctx.json(user);
    };

    @Override
    public void mapEndPoints(Javalin app) {
        app.post("/login",login);
    }
}
