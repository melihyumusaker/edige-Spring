package com.alibou.alibou.Controller;

import com.alibou.alibou.Core.IServices.IStudentCourseService;
import com.alibou.alibou.DTO.Course.AddNewCourseDTO;
import com.alibou.alibou.DTO.StudentCourse.AddNewStudentCourseAndCourseDTO;
import com.alibou.alibou.DTO.StudentCourse.AddNewStudentCourseDTO;
import com.alibou.alibou.DTO.StudentCourse.GetStudentsCoursesDTO;
import com.alibou.alibou.DTO.StudentCourse.StudentFinishHomeworkDTO;
import com.alibou.alibou.Model.Course;
import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Model.StudentCourse;
import com.alibou.alibou.Service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/addNewStudentCourse")
    public ResponseEntity<?> addNewStudentCourse(@RequestBody AddNewStudentCourseDTO request) {
        try {
            studentCourseService.addNewStudentCourse(request);
           return  ResponseEntity.ok("");
        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/addNewStudentCourseAndCourse")
    public ResponseEntity<?> addNewStudentCourseAndCourse(@RequestBody AddNewStudentCourseAndCourseDTO request) {
        try {
            studentCourseService.addNewStudentCourseAndCourse(request);
            return  ResponseEntity.ok("");
        } catch (RuntimeException e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping(path = "/get-all-students-courses" , produces = "application/json;charset=UTF-8")
    public ResponseEntity<List<Course>> getAllStudentsCourses(@RequestBody GetStudentsCoursesDTO request) {
        try {
            List<Course> courses = studentCourseService.getAllCoursesByStudentId(request.getStudent_id());
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PostMapping(path = "/get-students-done-courses", produces = "application/json;charset=UTF-8")
    public ResponseEntity<List<Course>> getStudentsDoneCourses(@RequestBody GetStudentsCoursesDTO request) {
        try {
            List<Course> courses = studentCourseService.getDoneCoursesByStudentIdAndIsHomeworkDone(request.getStudent_id());
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping(path = "/get-students-not-done-courses" , produces = "application/json;charset=UTF-8")
    public ResponseEntity<List<Course>> getStudentsNotDoneCourses(@RequestBody GetStudentsCoursesDTO request) {
        try {
            List<Course> courses = studentCourseService.getNotDoneCoursesByStudentIdAndIsHomeworkDone(request.getStudent_id());
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
