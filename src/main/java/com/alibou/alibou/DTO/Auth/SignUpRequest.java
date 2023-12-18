package com.alibou.alibou.DTO.Auth;

import lombok.Data;

import java.util.Date;

@Data
public class SignUpRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
    private Date birth_date;
    private String phone;
    private String city;
    private String section;
    private String school;
}
