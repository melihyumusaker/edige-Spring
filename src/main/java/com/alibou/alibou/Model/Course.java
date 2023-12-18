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
@Table(name = "Course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private int course_id;

    @Column(name = "course_name")
    private String course_name;

    @Column(name = "subcourse_name")
    private String subcourse_name;

    @Column(name = "homework_description")
    private String homework_description;

    @Column(name = "is_homework_done")
    private Integer is_homework_done;
}
