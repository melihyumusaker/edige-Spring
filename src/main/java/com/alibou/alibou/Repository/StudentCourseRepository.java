package com.alibou.alibou.Repository;

import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Model.StudentCourse;
import com.alibou.alibou.Model.Teacher;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentCourseRepository extends JpaRepository<StudentCourse,Integer> {

    @Query("SELECT sc FROM StudentCourse sc WHERE sc.student_id.student_id = ?1")
    List<StudentCourse> findAllByStudentId(int student_id);
    @Query("SELECT sc FROM StudentCourse sc WHERE sc.student_id.student_id = ?1 AND sc.course_id.is_homework_done = 1")
    List<StudentCourse> findAllByStudentIdAndIsHomeworkDone(int student_id);

    @Query("SELECT sc FROM StudentCourse sc WHERE sc.student_id.student_id = ?1 AND sc.course_id.is_homework_done = 0")
    List<StudentCourse> findAllByStudentIdAndIsHomeworkNotDone(int student_id);

    @Transactional
    @Modifying
    @Query("DELETE FROM StudentCourse sc WHERE sc.course_id.course_id = :courseId AND sc.student_id.student_id = :studentId")
    void deleteByCourseIdAndStudentId(@Param("courseId") int courseId, @Param("studentId") int studentId);

    @Query("SELECT COUNT(sc) FROM StudentCourse sc JOIN sc.course_id c WHERE sc.student_id.student_id = :studentId AND c.is_shown = 0")
    int countUnshownCoursesByStudentId(int studentId);

}
