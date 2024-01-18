package com.alibou.alibou.Core.IServices;

import com.alibou.alibou.Model.Course;
import com.alibou.alibou.Model.StudentCourse;

import java.util.List;

public interface IStudentCourseService {
    List<StudentCourse> getAllStudentAndCourses();

    List<Course> getDoneCoursesByStudentIdAndIsHomeworkDone(int student_id);
}
