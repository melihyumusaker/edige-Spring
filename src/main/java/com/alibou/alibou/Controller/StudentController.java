package com.alibou.alibou.Controller;

import com.alibou.alibou.Core.IServices.IStudentService;
import com.alibou.alibou.Core.IServices.IUserService;
import com.alibou.alibou.DTO.Parent.GetParentIdByUserIdDTO;
import com.alibou.alibou.DTO.Relation.SetRelationDTO;
import com.alibou.alibou.DTO.Student.GetStudentByIdRequestDTO;
import com.alibou.alibou.DTO.Student.GetStudentIdByUserIdDTO;
import com.alibou.alibou.DTO.Student.SetStudentsEnneagramResultDTO;
import com.alibou.alibou.Model.Relation;
import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final IStudentService studentService;
    private final IUserService userService;

    @Autowired
    public StudentController(IStudentService studentService,IUserService userService) {
        this.studentService = studentService;
        this.userService = userService;
    }

    @GetMapping(path = "/getAllStudents", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getAllStudents() {
        try{
            return ResponseEntity.ok(studentService.getAllStudents());
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping(path = "/getStudentById", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getStudentById(@RequestBody GetStudentByIdRequestDTO request) {
        int student_id = request.getStudent_id();

        Student studentDetails = studentService.getStudentDetailsById(student_id);
        if (studentDetails == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(studentDetails);
    }

    @PostMapping("/setRelation")
    public ResponseEntity<?> setRelation(@RequestBody SetRelationDTO request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        int userId = userService.getUserIdByEmail(userEmail);
        int studentId = studentService.getStudentIdByUserId(userId);

        Relation relation = studentService.postRelation(request.getTeacher_id(),studentId);
        return ResponseEntity.ok(relation);
    }

    @PostMapping("/getStudentIdByUserId")
    public ResponseEntity<?> getStudentIdByUserId(@RequestBody GetStudentIdByUserIdDTO request) {
        try {
            int student_id = studentService.getStudentIdByUserId(request.getUser_id());
            return ResponseEntity.ok(student_id);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/setStudentsEnneagramResult")
    public ResponseEntity<?> setStudentsEnneagramResult(@RequestBody SetStudentsEnneagramResultDTO request) {
        try {
            studentService.setEnneagramTestSolved(request.getStudent_id());
            studentService.postRelation(request.getTeacher_id() , request.getStudent_id());
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
