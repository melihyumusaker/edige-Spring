package com.alibou.alibou.Core.IServices;

import com.alibou.alibou.DTO.Relation.SetRelationDTO;
import com.alibou.alibou.Model.Relation;
import com.alibou.alibou.Model.Student;

import java.util.List;

public interface IStudentService {
    List<Student> getAllStudents();

    Student getStudentDetailsById(int studentId);

    Relation postRelation(SetRelationDTO request);
}
