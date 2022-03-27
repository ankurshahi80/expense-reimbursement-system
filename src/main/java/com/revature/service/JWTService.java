package com.revature.service;

import com.revature.model.User;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;


import java.security.Key;

public class JWTService {

    private Key key;

    {
        byte[] secret = "my_secret_password_asdfasdfasdfasdf".getBytes();
        key = Keys.hmacShaKeyFor(secret);
    }

    public String createJwt(User user){
        String jwt = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("user_id", user.getUserId())
                .claim("user_role", user.getUserRole())
                .signWith(key)
                .compact();

        return jwt;
    }

    public Jws<Claims> parseJwt(String jwt){
        try {
            Jws<Claims> token = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);

            return token;
        } catch (JwtException e) {
            throw new UnauthorizedResponse("JWT was invalid");
        }
    }
}
