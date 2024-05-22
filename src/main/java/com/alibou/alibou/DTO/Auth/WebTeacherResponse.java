package com.alibou.alibou.DTO.Auth;

import com.alibou.alibou.Core.Roles.Role;
import com.alibou.alibou.Model.Teacher;
import lombok.Data;

@Data
public class WebTeacherResponse {
    private String token;
    private String refreshToken;
    private Teacher teacher;
    private Role role;
}
