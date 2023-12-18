package com.alibou.alibou.Controller;

import com.alibou.alibou.Core.IServices.IStudentCourseService;
import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Model.StudentCourse;
import com.alibou.alibou.Service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/students-courses")
public class StudentCourseController {
    private final IStudentCourseService studentCourseService;

    @Autowired
    public StudentCourseController(IStudentCourseService studentCourseService) {
        this.studentCourseService = studentCourseService;
    }

    @GetMapping("/all")
    public List<StudentCourse> getAllStudents() {
        return studentCourseService.getAllStudentAndCourses();
    }

}
