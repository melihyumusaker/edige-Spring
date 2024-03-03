package com.alibou.alibou.DTO.WeeklyProgram;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WeeklyProgramDetailsDTO {
    private int weekly_program_id;
    private String lessonName;
    private String day;
    private String lessonStartHour;
    private String lessonEndHour;

    // Getters and setters for each field
}
