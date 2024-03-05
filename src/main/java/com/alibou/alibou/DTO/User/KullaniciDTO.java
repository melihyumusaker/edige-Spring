package com.alibou.alibou.DTO.User;

import com.alibou.alibou.Core.Roles.Role;
import lombok.Data;
import lombok.Builder;
import java.util.Date;

@Data
@Builder
public class KullaniciDTO {

    private int user_id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private Date birth_date;
    private String email;
    private String phone;
    private String city;
    private Role role;
    private Integer is_active;
}
