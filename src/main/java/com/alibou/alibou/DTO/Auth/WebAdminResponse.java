package com.alibou.alibou.DTO.Auth;

import com.alibou.alibou.Core.Roles.Role;
import com.alibou.alibou.Model.User;
import lombok.Data;

@Data
public class WebAdminResponse {
    private String token;
    private String refreshToken;
    private User user;
    private Role role;
}
