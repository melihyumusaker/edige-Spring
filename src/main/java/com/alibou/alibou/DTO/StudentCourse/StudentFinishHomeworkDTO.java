package com.alibou.alibou.DTO.StudentCourse;

import lombok.Data;

@Data
public class StudentFinishHomeworkDTO {
    private int course_id;
    private int is_homework_done;
    private String student_comment;
}
