package com.alibou.alibou.Repository;

import com.alibou.alibou.Model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting , Integer> {
    @Query("SELECT m FROM Meeting m WHERE m.relation_id.relation_id = :relationId ORDER BY m.start_day ASC, m.start_hour ASC")
    List<Meeting> findByRelationId(int relationId);

    @Query("SELECT m FROM Meeting m WHERE m.relation_id.relation_id IN :relationIds ORDER BY m.start_day ASC, m.start_hour ASC")
    List<Meeting> findByRelationIds(@Param("relationIds") List<Integer> relationIds);

    @Query("SELECT COUNT(me) FROM Meeting me WHERE me.relation_id.student_id.student_id= :studentId AND me.is_shown = 0")
    int countByStudentIdAndIsShownFalse(int studentId);

    @Query("SELECT m FROM Meeting m WHERE m.createdAt < :dateTime AND m.is_shown = 0")
    List<Meeting> findMeetingsToBeShown(@Param("dateTime") LocalDateTime dateTime);

}
