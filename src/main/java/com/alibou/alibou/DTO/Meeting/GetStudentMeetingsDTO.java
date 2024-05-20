package com.alibou.alibou.DTO.Meeting;

import lombok.Data;

@Data
public class GetStudentMeetingsDTO {
    private int student_id;
    private int teacher_id;
}