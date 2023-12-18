package com.alibou.alibou.DTO.Auth;

import lombok.Data;

import java.util.Date;

@Data
public class SignUpTeacherRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phone;
    private String city;
    private Date birth_date;
    private String expertise;
}
