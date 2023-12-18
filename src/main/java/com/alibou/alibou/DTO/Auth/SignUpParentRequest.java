package com.alibou.alibou.DTO.Auth;

import com.alibou.alibou.Model.Student;
import lombok.Data;

import java.util.Date;

@Data
public class SignUpParentRequest {
    private String name;
    private String surname;
    private String email;
    private String password;
    private Date birth_date;
    private String phone;
    private String city;
    private Student student;
}
