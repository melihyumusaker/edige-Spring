package com.alibou.alibou.DTO.Meeting;

import lombok.Data;

@Data
public class UpdateMeetingDTO {
    private int meeting_id;
    private String teacher_comment;
    private Integer is_student_join;
    private Integer is_parent_join;
}
