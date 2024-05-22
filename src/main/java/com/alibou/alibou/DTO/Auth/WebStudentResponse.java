package com.alibou.alibou.DTO.Auth;

import com.alibou.alibou.Core.Roles.Role;
import com.alibou.alibou.Model.Student;
import lombok.Data;

@Data
public class WebStudentResponse {

    private String token;
    private String refreshToken;
    private Student student;
    private Role role;
}
