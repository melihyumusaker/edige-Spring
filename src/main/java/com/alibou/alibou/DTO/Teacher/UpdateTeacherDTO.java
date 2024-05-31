package com.alibou.alibou.DTO.Teacher;

import lombok.Data;

import java.util.Date;

@Data
public class UpdateTeacherDTO {
    private int teacher_id;
    private String name;
    private String surname;
    private Date birth_date;
    private String email;
    private String phone;
    private String city;
    private String enneagram_result;
    private String about;
    private String expertise;
    private String username;
}
