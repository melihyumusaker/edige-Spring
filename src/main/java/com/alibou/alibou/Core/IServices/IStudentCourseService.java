package com.alibou.alibou.Core.IServices;

import com.alibou.alibou.DTO.StudentCourse.AddNewStudentCourseAndCourseDTO;
import com.alibou.alibou.DTO.StudentCourse.AddNewStudentCourseDTO;
import com.alibou.alibou.DTO.StudentCourse.StudentFinishHomeworkDTO;
import com.alibou.alibou.Model.Course;
import com.alibou.alibou.Model.StudentCourse;

import java.util.List;

public interface IStudentCourseService {
    List<StudentCourse> getAllStudentAndCourses();

    List<Course> getDoneCoursesByStudentIdAndIsHomeworkDone(int student_id);

    List<Course> getNotDoneCoursesByStudentIdAndIsHomeworkDone(int studentId);

    List<Course> getAllCoursesByStudentId(int studentId);


    void addNewStudentCourse(AddNewStudentCourseDTO request);

    void addNewStudentCourseAndCourse(AddNewStudentCourseAndCourseDTO request);
    int countUnshownCoursesByStudentId(int studentId);
}
