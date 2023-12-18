package com.alibou.alibou.Repository;

import com.alibou.alibou.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}
