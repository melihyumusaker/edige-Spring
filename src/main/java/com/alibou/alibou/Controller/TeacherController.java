package com.alibou.alibou.Controller;
import com.alibou.alibou.Core.IServices.IMeetingService;
import com.alibou.alibou.Core.IServices.IUserService;
import com.alibou.alibou.DTO.Parent.GetParentIdByUserIdDTO;
import com.alibou.alibou.DTO.Student.GetAllStudentResponseDTO;
import com.alibou.alibou.DTO.Teacher.*;
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

    @GetMapping(path = "/getAllTeachers", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getAllTeachers() {
        try{
            return ResponseEntity.ok(teacherService.getAllTeachers());
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping(path = "/showStudents", produces = "application/json;charset=UTF-8")
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

    @PostMapping(path = "/showStudents1", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> showStudents1(@RequestBody ShowStudents1DTO request) {
        try{
            List<GetAllStudentResponseDTO> students = teacherService.getStudentsByTeacherId1(request.getTeacher_id());
            return ResponseEntity.ok(students);
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/getTeacherIdByUserId")
    public ResponseEntity<?> getTeacherIdByUserId(@RequestBody GetTeacherIdByUserIdDTO request) {
        try {
            int teacher_id = teacherService.getTeacherIdByUserId(request.getUser_id());
            return ResponseEntity.ok(teacher_id);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping(path = "/getTeachersByStudentType", produces = "application/json;charset=UTF-8")
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

    @PostMapping(path = "/getTeacherInfo", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getTeacherInfo() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();

            int userId = userService.getUserIdByEmail(userEmail);
            int teacherId = teacherService.getTeacherIdByUserId(userId);

            Teacher teacherDetails = teacherService.getTeacherDetails(teacherId);

            return ResponseEntity.ok(teacherDetails);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/updateTeacherEnneagramTypeAndAbout")
    public ResponseEntity<?> updateTeacherEnneagramTypeAndAbout(@RequestBody UpdateTeacherEnneagramTypeAndAboutDTO request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();

            int userId = userService.getUserIdByEmail(userEmail);
            int teacherId = teacherService.getTeacherIdByUserId(userId);

            boolean isUpdated = teacherService.updateTeacherAboutAndEnneagramTestSolved(request , teacherId);

            if (isUpdated) {
                return ResponseEntity.ok("Teacher updated successfully");
            } else {
                return ResponseEntity.badRequest().body("Teacher not found or invalid data");
            }
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/updateTeacher")
    public ResponseEntity<?> updateTeacher(@RequestBody UpdateTeacherDTO request) {
        try {

            boolean isUpdated = teacherService.updateTeacher(request);

            if (isUpdated) {
                return ResponseEntity.ok("Teacher updated successfully");
            } else {
                return ResponseEntity.badRequest().body("Teacher not found or invalid data");
            }
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
