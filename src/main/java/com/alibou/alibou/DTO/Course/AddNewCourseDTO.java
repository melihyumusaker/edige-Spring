package com.alibou.alibou.DTO.Course;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddNewCourseDTO {
    private String course_name;
    private String subcourse_name;
    private String homework_description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime homework_deadline;
}
