package com.alibou.alibou.Repository;

import com.alibou.alibou.Model.WeeklyProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WeeklyProgramRepository extends JpaRepository<WeeklyProgram , Integer> {
    @Query("SELECT wp FROM WeeklyProgram wp WHERE wp.student_id.student_id = :studentId")
    List<WeeklyProgram> findWeeklyProgramsByStudentId(@Param("studentId") int studentId);
}
