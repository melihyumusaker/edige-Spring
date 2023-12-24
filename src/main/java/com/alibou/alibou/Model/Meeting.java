package com.alibou.alibou.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "meeting")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id")
    private int meeting_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "relation_id")
    private Relation relation_id;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "teacher_comment")
    private String teacher_comment;

    @Column(name = "is_student_join")
    private Integer is_student_join;

    @Column(name = "is_parent_join")
    private Integer is_parent_join;
}
