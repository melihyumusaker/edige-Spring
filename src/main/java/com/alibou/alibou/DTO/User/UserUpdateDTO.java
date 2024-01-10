package com.alibou.alibou.DTO.User;

import com.alibou.alibou.Core.Roles.Role;
import lombok.Data;

import java.util.Date;

@Data
public class UserUpdateDTO {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String city;
    private String username;
    private Date birth_date;
    private Role role;
}
