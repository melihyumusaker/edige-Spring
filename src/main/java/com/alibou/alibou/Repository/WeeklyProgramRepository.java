package com.alibou.alibou.Repository;

import com.alibou.alibou.Model.WeeklyProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WeeklyProgramRepository extends JpaRepository<WeeklyProgram , Integer> {
    @Query("SELECT wp FROM WeeklyProgram wp WHERE wp.student_id.student_id = :studentId ORDER BY CASE wp.day WHEN 'Pazartesi' THEN 1 WHEN 'Sali' THEN 2 WHEN 'Carsamba' THEN 3 WHEN 'Persembe' THEN 4 WHEN 'Cuma' THEN 5 WHEN 'Cumartesi' THEN 6 WHEN 'Pazar' THEN 7 END")
    List<WeeklyProgram> findWeeklyProgramsByStudentId(@Param("studentId") int studentId);

}
