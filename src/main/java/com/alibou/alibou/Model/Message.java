package com.alibou.alibou.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private int message_id;

    @Column(name = "sender_id")
    private int sender_id;

    @Column(name = "receiver_id")
    private int receiver_id;

    @Column(name = "message_content")
    private String message_content;

    @Column(name = "send_date")
    private Date send_date;
}
