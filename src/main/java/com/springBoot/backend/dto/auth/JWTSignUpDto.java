package com.springBoot.backend.dto.auth;

import com.springBoot.backend.annotation.valid.Email;
import com.springBoot.backend.annotation.valid.Password;
import jakarta.validation.constraints.Size;

public class JWTSignUpDto {

    public record Req(
            @Email
            String accountId,
            @Password
            String password,
            @Size(max = 256)
            String userName,
            @Size(min = 9, max = 11)
            String phoneNumber
    ){}

    public record Res(String result){}


}
