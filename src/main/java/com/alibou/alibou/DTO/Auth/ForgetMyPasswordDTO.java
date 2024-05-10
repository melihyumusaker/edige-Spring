package com.alibou.alibou.DTO.Auth;

import lombok.Data;

@Data
public class ForgetMyPasswordDTO {
    private String oldPassword;
    private String newPassword;
    private String email;
}
