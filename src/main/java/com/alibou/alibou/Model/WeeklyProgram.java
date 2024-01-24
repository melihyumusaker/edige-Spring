package com.alibou.alibou.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "weekly_program")
public class WeeklyProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weekly_program_id")
    private int weekly_program_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id")
    private Student student_id;

    @Column(name = "lesson_name")
    private String lesson_name;

    @Column(name = "day")
    private String day;

    @Column(name = "lesson_start_hour")
    private String lesson_start_hour;

    @Column(name = "lesson_end_hour")
    private String lesson_end_hour;

}
