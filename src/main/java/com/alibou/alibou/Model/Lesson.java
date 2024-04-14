package com.alibou.alibou.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private int lesson_id;

    @Column(name = "grade")
    private String grade;

    @Column(name = "lesson_name")
    private String lesson_name;

    @Column(name = "sublesson_name")
    private String sublesson_name;

    @Column(name = "sublesson_name_detail")
    private String sublesson_name_detail;
}
