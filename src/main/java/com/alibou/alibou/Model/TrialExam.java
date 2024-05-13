package com.alibou.alibou.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

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
    private LocalDate date;

    @Column(name = "turkce_true")
    private float    turkce_true;

    @Column(name = "turkce_false")
    private float    turkce_false;

    @Column(name = "turkce_net")
    private float    turkce_net;

    @Column(name = "mat_true")
    private float    mat_true;

    @Column(name = "mat_false")
    private float    mat_false;

    @Column(name = "mat_net")
    private float    mat_net;

    @Column(name = "fen_true")
    private float    fen_true;

    @Column(name = "fen_false")
    private float     fen_false;

    @Column(name = "fen_net")
    private float    fen_net;

    @Column(name = "sosyal_true")
    private float    sosyal_true;

    @Column(name = "sosyal_false")
    private float    sosyal_false;

    @Column(name = "sosyal_net")
    private float    sosyal_net;

    @Column(name = "net")
    private float    net;

    @Column(name = "exam_name")
    private String exam_name;

    @Column(name = "is_shown")
    private int is_shown;
}
