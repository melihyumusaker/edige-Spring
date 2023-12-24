package com.alibou.alibou.DTO.WeeklyProgram;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateWeeklyProgramDTO {
    private Integer weekly_program_id;
    private String lesson_name;
    private LocalDate day;
    private String lesson_start_hour;
    private String lesson_end_hour;
}
