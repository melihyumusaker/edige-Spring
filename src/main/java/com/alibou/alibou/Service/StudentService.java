package com.alibou.alibou.Service;

import com.alibou.alibou.Core.IServices.IStudentService;
import com.alibou.alibou.DTO.Relation.SetRelationDTO;
import com.alibou.alibou.Model.Relation;
import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Model.Teacher;
import com.alibou.alibou.Repository.RelationRepository;
import com.alibou.alibou.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService implements IStudentService {
    private final StudentRepository studentRepository;
    private final RelationRepository relationRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository,RelationRepository relationRepository){
        this.studentRepository = studentRepository;
        this.relationRepository = relationRepository;
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentDetailsById(int studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return null;
        }else{
            return student;
        }
    }

    @Override
    public Relation postRelation(SetRelationDTO request) {
        Relation relation = new Relation();

        Student student = new Student();
        student.setStudent_id(request.getStudent_id());

        Teacher teacher = new Teacher();
        teacher.setTeacher_id(request.getTeacher_id());

        relation.setStudent_id(student);
        relation.setTeacher_id(teacher);

        relationRepository.save(relation);

        return relation;
    }

}
