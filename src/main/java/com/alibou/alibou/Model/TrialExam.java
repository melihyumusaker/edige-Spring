package com.alibou.alibou.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "trial_exam")
public class TrialExam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trial_exam_id")
    private int trial_exam_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Student student_id;

    @Column(name = "date")
    private Date date;

    @Column(name = "turkce_true")
    private double  turkce_true;

    @Column(name = "turkce_false")
    private double  turkce_false;

    @Column(name = "turkce_net")
    private double  turkce_net;

    @Column(name = "mat_true")
    private double  mat_true;

    @Column(name = "mat_false")
    private double  mat_false;

    @Column(name = "mat_net")
    private double  mat_net;

    @Column(name = "fen_true")
    private double  fen_true;

    @Column(name = "fen_false")
    private double   fen_false;

    @Column(name = "fen_net")
    private double  fen_net;

    @Column(name = "sosyal_true")
    private double  sosyal_true;

    @Column(name = "sosyal_false")
    private double  sosyal_false;

    @Column(name = "sosyal_net")
    private double  sosyal_net;

    @Column(name = "net")
    private double  net;

    @Column(name = "exam_name")
    private String exam_name;
}
