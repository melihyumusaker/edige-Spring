package com.alibou.alibou.DTO.Meeting;

import com.alibou.alibou.Model.Relation;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class CreateMeetingDTO {
    private int student_id;
    private int teacher_id;
    private String title;
    private String description;
    private LocalDate start_day;
    private LocalTime start_hour;
    private String location;
}
