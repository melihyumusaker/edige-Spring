package com.alibou.alibou.DTO.Auth;

import lombok.Data;

@Data
public class SigninRequest {
    private String email;
    private String password;
}
