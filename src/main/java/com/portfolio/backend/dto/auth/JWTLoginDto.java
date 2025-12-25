package com.portfolio.backend.dto.auth;


import com.portfolio.backend.annotation.valid.Email;

public class JWTLoginDto {

    public record Req(
            @Email
            String email,
            String password
    ){}

    public record Res(boolean isLogin, String accessToken){

        public Res(boolean isLogin){
            this(isLogin, null);
        }

    }

}
