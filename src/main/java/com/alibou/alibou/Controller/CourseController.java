package com.alibou.alibou.Controller;

import com.alibou.alibou.Core.IServices.ICourseService;
import com.alibou.alibou.DTO.StudentCourse.StudentFinishHomeworkDTO;
import com.alibou.alibou.Model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final ICourseService courseService;

    @Autowired
    public CourseController(ICourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/all")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @PutMapping("/studentFinishHomework")
    public ResponseEntity<?> studentFinishHomework(@RequestBody StudentFinishHomeworkDTO request) {
        try {
            courseService.studentFinishHomework(request);
            return ResponseEntity.ok("Öğrenci ödevi güncelledi");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
