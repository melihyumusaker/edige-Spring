package com.alibou.alibou.Repository;

import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Model.StudentCourse;
import com.alibou.alibou.Model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentCourseRepository extends JpaRepository<StudentCourse,Integer> {
    @Query("SELECT sc FROM StudentCourse sc WHERE sc.student_id.student_id = ?1 AND sc.course_id.is_homework_done = 1")
    List<StudentCourse> findAllByStudentIdAndIsHomeworkDone(int student_id);
}
