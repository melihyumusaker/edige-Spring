package com.alibou.alibou.Core.IServices;

import com.alibou.alibou.DTO.StudentCourse.StudentFinishHomeworkDTO;
import com.alibou.alibou.Model.Course;

import java.util.List;

public interface ICourseService {
    List<Course> getAllCourses();

    void studentFinishHomework(StudentFinishHomeworkDTO request);
}
