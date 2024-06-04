package com.alibou.alibou.DTO.Student;

import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Data
@Builder
public class GetAllStudentResponseDTO {
    private int student_id;
    private String name;
    private String surname;
    private Date birth_date;
    private String email;
    private String phone;
    private String city;
    private String enneagram_result;
    private String section;
    private String school;
    private String coachName;
    private String username;
}
