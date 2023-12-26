package com.alibou.alibou.DTO.TrialExam;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GetStudentTrialExamsDTO {
    private LocalDate date;
    private float turkce_true;
    private float turkce_false;
    private float mat_true;
    private float mat_false;
    private float fen_true;
    private float fen_false;
    private float sosyal_true;
    private float sosyal_false;
    private String exam_name;
}
