package com.alibou.alibou.Controller;

import com.alibou.alibou.Core.IServices.IStudentService;
import com.alibou.alibou.DTO.Student.GetStudentByIdRequestDTO;
import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final IStudentService studentService;

    @Autowired
    public StudentController(IStudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/getStudentById")
    public ResponseEntity<?> getStudentById(@RequestBody GetStudentByIdRequestDTO request) {
        int student_id = request.getStudent_id();

        Student studentDetails = studentService.getStudentDetailsById(student_id);
        if (studentDetails == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(studentDetails);
    }
}
