package com.alibou.alibou.DTO.TrialExam;

import com.alibou.alibou.Model.Student;
import lombok.Data;
import java.time.LocalDate;
import java.util.Date;


@Data
public class SetTrialExamDTO {

    private int student_id;
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
