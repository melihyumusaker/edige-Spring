package com.alibou.alibou.Repository;

import com.alibou.alibou.Model.StudentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentRecordRepository extends JpaRepository<StudentRecord, Integer> {

    @Query("select sr from StudentRecord sr where sr.student_id.user.user_id = :userId and FUNCTION('DATE', sr.date) = :today")
    Optional<StudentRecord> findTodayRecordByStudentId(@Param("userId") int userId, @Param("today") LocalDate today);


    @Query("select sr from StudentRecord sr where sr.student_id.student_id= :studentId")
    List<StudentRecord> findAllRecordsByStudentId(@Param("studentId") int studentId);

    @Query("select count(sr) from StudentRecord sr where sr.student_id.student_id = :studentId and sr.exit_time is null")
    int countRecordsWithNullExitTime(@Param("studentId") int studentId);
}
