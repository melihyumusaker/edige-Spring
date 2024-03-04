package com.alibou.alibou.Core.IServices;

import com.alibou.alibou.DTO.Course.AddNewCourseDTO;
import com.alibou.alibou.DTO.Course.DeleteCourseDTO;
import com.alibou.alibou.DTO.Course.UpdateCourseDTO;
import com.alibou.alibou.DTO.StudentCourse.StudentFinishHomeworkDTO;
import com.alibou.alibou.Model.Course;

import java.util.List;

public interface ICourseService {
    List<Course> getAllCourses();

    void studentFinishHomework(StudentFinishHomeworkDTO request);

    int addNewCourse(AddNewCourseDTO request);

    void deleteCourse(DeleteCourseDTO request);

    boolean updateCourse(UpdateCourseDTO request);
}
