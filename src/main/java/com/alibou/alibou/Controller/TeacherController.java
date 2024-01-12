package com.alibou.alibou.Controller;
import com.alibou.alibou.Core.IServices.IMeetingService;
import com.alibou.alibou.Core.IServices.IUserService;
import com.alibou.alibou.DTO.Parent.GetParentIdByUserIdDTO;
import com.alibou.alibou.DTO.Teacher.GetTeacherIdByUserIdDTO;
import com.alibou.alibou.DTO.Teacher.GetTeachersByStudentTypeDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.alibou.alibou.Core.IServices.ITeacherService;
import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Model.Teacher;
import com.alibou.alibou.Service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final ITeacherService teacherService;
    private final IUserService userService;

    @Autowired
    public TeacherController(ITeacherService teacherService , IUserService userService) {
        this.teacherService = teacherService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/showStudents")
    public ResponseEntity<List<Student>> showStudents() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        int userId = userService.getUserIdByEmail(userEmail);
        int teacherId = teacherService.getTeacherIdByUserId(userId);

        List<Student> students = teacherService.getStudentsByTeacherId(teacherId);

        if (students.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(students);
        }

        return ResponseEntity.ok(students);
    }

    @GetMapping("/getTeacherIdByUserId")
    public ResponseEntity<?> getTeacherIdByUserId(@RequestBody GetTeacherIdByUserIdDTO request) {
        try {
            int teacher_id = teacherService.getTeacherIdByUserId(request.getUser_id());
            return ResponseEntity.ok(teacher_id);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/getTeachersByStudentType")
    public ResponseEntity<?> getTeachersByStudentType(@RequestBody GetTeachersByStudentTypeDTO request) {
        try {
            List<Teacher> teachers = teacherService.getTeachersByStudentType(request.getStudentType());

            if (teachers.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(teachers);
            }

            return ResponseEntity.ok(teachers);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
