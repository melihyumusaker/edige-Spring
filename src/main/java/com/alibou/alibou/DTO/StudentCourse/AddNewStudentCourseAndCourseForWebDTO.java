package com.alibou.alibou.DTO.StudentCourse;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AddNewStudentCourseAndCourseForWebDTO {
    private int student_id;
    private String course_name;
    private String subcourse_name;
    private String homework_description;
    private LocalDate date;
    private LocalTime time;
}
