package com.alibou.alibou.Service;

import com.alibou.alibou.Core.IServices.IStudentCourseService;
import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Model.StudentCourse;
import com.alibou.alibou.Repository.StudentCourseRepository;
import com.alibou.alibou.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentCourseService implements IStudentCourseService {
    private final StudentCourseRepository studentCourseRepository;

    @Autowired
    public StudentCourseService(StudentCourseRepository studentCourseRepository){
        this.studentCourseRepository = studentCourseRepository;
    }

    public List<StudentCourse> getAllStudentAndCourses(){
        return studentCourseRepository.findAll();
    }

}
