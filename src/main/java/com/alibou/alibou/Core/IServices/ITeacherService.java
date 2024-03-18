package com.alibou.alibou.Core.IServices;

import com.alibou.alibou.DTO.Teacher.UpdateTeacherEnneagramTypeAndAboutDTO;
import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Model.Teacher;

import java.util.List;

public interface ITeacherService {
    List<Teacher> getAllTeachers();
    List<Student> getStudentsByTeacherId(int teacherId);
    int getTeacherIdByUserId(int userId);
    List<Teacher> getTeachersByStudentType(String studentType);
    Teacher getTeacherDetails(int teacherId);

    boolean updateTeacherAboutAndEnneagramTestSolved(UpdateTeacherEnneagramTypeAndAboutDTO request, int teacherId);
    Teacher getTeacherByUserId(int userId);
}
