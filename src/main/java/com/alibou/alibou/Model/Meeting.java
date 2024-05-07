package com.alibou.alibou.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

    @Column(name = "is_student_join")
    private Integer is_student_join;

    @Column(name = "is_parent_join")
    private Integer is_parent_join;

    @Column(name = "description")
    private String description;

    @Column(name = "start_day")
    private LocalDate start_day;

    @Column(name = "start_hour")
    private LocalTime start_hour;

    @Column(name = "title")
    private String title;

    @Column(name = "location")
    private String location;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
