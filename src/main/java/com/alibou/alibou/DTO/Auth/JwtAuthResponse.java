package com.alibou.alibou.DTO.Auth;

import com.alibou.alibou.Core.Roles.Role;
import lombok.Data;

@Data
public class JwtAuthResponse {
    private String token;
    private String refreshToken;
    private Role role;
}
