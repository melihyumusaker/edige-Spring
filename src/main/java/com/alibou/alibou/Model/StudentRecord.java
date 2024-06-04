package com.alibou.alibou.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "student_records")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student_id;

    @Column(name="date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "entry_time")
    @Temporal(TemporalType.TIME)
    private LocalTime entry_time;

    @Column(name = "exit_time")
    @Temporal(TemporalType.TIME)
    private Date exit_time;

    @Column(name = "status" , nullable = false, length = 255)
    private String status;
}
