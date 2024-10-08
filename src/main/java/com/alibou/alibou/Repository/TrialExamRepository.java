package com.alibou.alibou.Repository;

import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Model.TrialExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrialExamRepository extends JpaRepository<TrialExam , Integer> {
    @Query("SELECT te FROM TrialExam te WHERE te.student_id.student_id = :student_id")
    List<TrialExam> findAllByStudentId(@Param("student_id") int student_id);

    @Query("SELECT te FROM TrialExam te WHERE te.student_id.student_id = :studentId ORDER BY te.date DESC")
    List<TrialExam> findAllByStudentIdOrderByDateAsc(int studentId);
    @Query("SELECT COUNT(te) FROM TrialExam te WHERE te.student_id.student_id = :studentId AND te.is_shown = 0")
    int countByStudentIdAndIsShownFalse(int studentId);
}

