package com.alibou.alibou.Core.IServices;

import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Model.Teacher;

import java.util.List;

public interface ITeacherService {
    List<Teacher> getAllTeachers();
    List<Student> getStudentsByTeacherId(int teacherId);
    int getTeacherIdByUserId(int userId);
}
