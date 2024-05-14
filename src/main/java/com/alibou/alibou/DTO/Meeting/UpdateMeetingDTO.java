package com.alibou.alibou.DTO.Meeting;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class UpdateMeetingDTO {
    private int meeting_id;
    private Integer is_student_join;
    private Integer is_parent_join;
    private String description;
    private LocalDate start_day;
    private LocalTime start_hour;
    private String title;
    private String location;
    private String teacher_comment;
}
