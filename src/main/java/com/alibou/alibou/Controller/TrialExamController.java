package com.alibou.alibou.Controller;

import com.alibou.alibou.Core.IServices.IStudentService;
import com.alibou.alibou.Core.IServices.ITrialExamService;
import com.alibou.alibou.Core.IServices.IUserService;
import com.alibou.alibou.DTO.Course.DeleteCourseDTO;
import com.alibou.alibou.DTO.TrialExam.*;
import com.alibou.alibou.DTO.WeeklyProgram.UpdateWeeklyProgramDTO;
import com.alibou.alibou.Model.TrialExam;
import com.alibou.alibou.Service.TrialExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trial-exams")
public class TrialExamController {
    private final ITrialExamService trialExamService;
    private final IStudentService studentService;
    private final IUserService userService;

    @Autowired
    public TrialExamController(ITrialExamService trialExamService,IStudentService studentService,IUserService userService) {
        this.trialExamService = trialExamService;
        this.studentService = studentService;
        this.userService = userService;
    }

    @PostMapping("/setStudentTrialExamResult")
    public ResponseEntity<String> setStudentTrialExamResult(@RequestBody SetTrialExamDTO request){
        boolean isCreated = trialExamService.saveStudentTrialExamResult(request);
        if (isCreated) {
            return ResponseEntity.ok("Trial exam result saved successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save trial exam result");
        }
    }

    @GetMapping (path = "/getStudentTrialExams", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getStudentTrialExams(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        int userId = userService.getUserIdByEmail(userEmail);
        int studentId = studentService.getStudentIdByUserId(userId);
        List<GetStudentTrialExamsDTO> studentTrialExams = trialExamService.getAllTrialExamsByStudentId(studentId);

        if (studentTrialExams.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(studentTrialExams, HttpStatus.OK);
    }

    @PostMapping (path = "/getStudentTrialExamsByTeacher", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getStudentTrialExamsByTeacher(@RequestBody GetStudentTrialExamsByTeacherDTO request){
        try {
            List<GetStudentTrialExamsDTO> studentTrialExams = trialExamService.getAllTrialExamsByStudentId(request.getStudent_id());
            return new ResponseEntity<>(studentTrialExams, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while processing your request: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteTrialExam")
    public ResponseEntity<?> deleteTrialExam(@RequestBody DeleteTrialExamDTO request) {
        try {
            trialExamService.deleteTrialExam(request);
            return ResponseEntity.ok("Trial Exam silindi");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Trial Exam silinirken bir hata oluştu: " + e.getMessage());
        }
    }

    @PutMapping("/updateTrialExam")
    public ResponseEntity<String> updateTrialExam(@RequestBody TrialExamUpdateDTO request){
        boolean isUpdated = trialExamService.updateTrialExam(request);

        if (isUpdated) {
            return ResponseEntity.ok("Trial Exam updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Trial Exam not found or invalid data");
        }
    }

    @PutMapping("/updateTrialExamIsShownValue")
    public ResponseEntity<?> updateTrialExamIsShownValue(@RequestBody UpdateTrialExamIsShownValueDTO request){
        boolean isUpdated = trialExamService.updateTrialExamIsShownValue(request);

        if (isUpdated) {
            return ResponseEntity.ok("Trial Exam updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Trial Exam not found or invalid data");
        }
    }

    @PostMapping("/countUnshown")
    public ResponseEntity<?> countUnshownExams(@RequestBody GetIsShownNumberDTO request) {
        try {
            int count = trialExamService.countUnshownExamsByStudentId(request);
            return ResponseEntity.ok(count);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
        }
    }

}
