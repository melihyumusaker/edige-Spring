package com.alibou.alibou.DTO.Teacher;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class GetAllTeacherDTO {
    private String name;
    private String surname;
    private Date birth_date;
    private String email;
    private String phone;
    private String city;
    private String enneagram_result;
    private String about;
    private String expertise;
}
