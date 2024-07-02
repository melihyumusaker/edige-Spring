package com.alibou.alibou.Repository;

import com.alibou.alibou.Model.NotificationStudent;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationStudentRepository extends JpaRepository<NotificationStudent, Integer> {
    @Query("SELECT ns FROM NotificationStudent ns WHERE ns.student_id.student_id = :studentId")
    List<NotificationStudent> findAllByStudentId(@Param("studentId") int studentId);

    @Query("SELECT COUNT(ns) FROM NotificationStudent ns WHERE ns.student_id.student_id = :studentId AND ns.is_shown = false")
    int findUnseenNotifNumber(@Param("studentId") int studentId);

    @Modifying
    @Transactional
    @Query("UPDATE NotificationStudent ns SET ns.is_shown = true WHERE ns.student_id.student_id = :studentId")
    void setAllNotifsUnshownValue1(@Param("studentId") int studentId);
}
