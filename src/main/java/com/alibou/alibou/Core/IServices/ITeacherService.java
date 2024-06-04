package com.alibou.alibou.Core.IServices;

import com.alibou.alibou.DTO.Student.GetAllStudentResponseDTO;
import com.alibou.alibou.DTO.Teacher.GetAllTeacherDTO;
import com.alibou.alibou.DTO.Teacher.UpdateTeacherDTO;
import com.alibou.alibou.DTO.Teacher.UpdateTeacherEnneagramTypeAndAboutDTO;
import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Model.Teacher;

import java.util.List;

public interface ITeacherService {
    List<GetAllTeacherDTO> getAllTeachers();
    List<Student> getStudentsByTeacherId(int teacherId);
    List<GetAllStudentResponseDTO> getStudentsByTeacherId1(int teacherId);
    int getTeacherIdByUserId(int userId);
    List<Teacher> getTeachersByStudentType(String studentType);
    Teacher getTeacherDetails(int teacherId);

    boolean updateTeacherAboutAndEnneagramTestSolved(UpdateTeacherEnneagramTypeAndAboutDTO request, int teacherId);
    Teacher getTeacherByUserId(int userId);

    boolean updateTeacher(UpdateTeacherDTO request);
}
