package com.alibou.alibou.DTO.TrialExam;

import com.alibou.alibou.Model.Student;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class SetTrialExamDTO {

    private Student student_id;
    private Date date;
    private double  turkce_true;
    private double  turkce_false;
    private double  mat_true;
    private double  mat_false;
    private double  fen_true;
    private double  fen_false;
    private double  sosyal_true;
    private double  sosyal_false;
    private String exam_name;
}
