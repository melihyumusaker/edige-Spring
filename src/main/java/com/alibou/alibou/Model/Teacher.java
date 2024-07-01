package com.alibou.alibou.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "Teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private int teacher_id;

    @Column(name = "expertise")
    private String expertise;

    @Column(name = "enneagram_result")
    private String enneagram_result;

    @Column(name = "about")
    private String about;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_enneagram_test_solved")
    private Integer is_enneagram_test_solved;

    @Column(name = "student_number")
    private Integer student_number;
}
