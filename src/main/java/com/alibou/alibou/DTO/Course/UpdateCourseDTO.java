package com.alibou.alibou.DTO.Course;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateCourseDTO {
    private int course_id;
    private String course_name;
    private String subcourse_name;
    private String homework_description;
    private Integer is_homework_done;
    private LocalDateTime homework_deadline;
    private Integer is_shown;
}
