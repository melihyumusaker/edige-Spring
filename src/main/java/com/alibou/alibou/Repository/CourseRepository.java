package com.alibou.alibou.Repository;

import com.alibou.alibou.Model.Course;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Transactional
    @Modifying
    @Query("UPDATE Course c SET c.is_homework_done = :isHomeworkDone, c.student_comment = :studentComment WHERE c.course_id = :courseId")
    void updateHomeworkStatus(@Param("courseId") int courseId, @Param("isHomeworkDone") int isHomeworkDone, @Param("studentComment") String studentComment);
}
