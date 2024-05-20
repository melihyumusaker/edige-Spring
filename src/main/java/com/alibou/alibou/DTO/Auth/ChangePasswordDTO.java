package com.alibou.alibou.DTO.Auth;

import lombok.Data;

@Data
public class ChangePasswordDTO {
    private int user_id;
    private String newPassword;
}
