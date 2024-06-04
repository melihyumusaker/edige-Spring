package com.alibou.alibou.Core.IServices;

import com.alibou.alibou.DTO.Relation.SetRelationDTO;
import com.alibou.alibou.DTO.Student.GetAllStudentResponseDTO;
import com.alibou.alibou.Model.Relation;
import com.alibou.alibou.Model.Student;

import java.util.List;

public interface IStudentService {
    List<GetAllStudentResponseDTO> getAllStudents();

    Student getStudentDetailsById(int studentId);

    Relation postRelation(int teacher_id , int studentId);
    int getStudentIdByUserId(int userId);

    void setEnneagramTestSolved(int studentId);

    boolean updateStudent(GetAllStudentResponseDTO request);
}
