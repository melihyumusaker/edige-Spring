package com.alibou.alibou.Repository;

import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Model.StudentCourse;
import com.alibou.alibou.Model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudentCourseRepository extends JpaRepository<StudentCourse,Integer> {

}
