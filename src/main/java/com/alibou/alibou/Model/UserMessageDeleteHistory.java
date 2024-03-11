package com.alibou.alibou.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "usermessagedeletehistory")
public class UserMessageDeleteHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user1_id")
    private int user1_id;

    @Column(name = "user2_id")
    private int user2_id;

    @Column(name = "is_user1_deleted")
    private boolean is_user1_deleted;

    @Column(name = "is_user2_deleted")
    private boolean is_user2_deleted;
}
