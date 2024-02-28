package com.alibou.alibou.Controller;

import com.alibou.alibou.Core.IServices.IStudentService;
import com.alibou.alibou.Core.IServices.ITrialExamService;
import com.alibou.alibou.Core.IServices.IUserService;
import com.alibou.alibou.DTO.TrialExam.GetStudentTrialExamsByTeacherDTO;
import com.alibou.alibou.DTO.TrialExam.GetStudentTrialExamsDTO;
import com.alibou.alibou.DTO.TrialExam.SetTrialExamDTO;
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

    @GetMapping("/all")
    public List<TrialExam> getAllTrialExams() {
        return trialExamService.getAllTrialExams();
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

    @GetMapping ("/getStudentTrialExams")
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

    @PostMapping ("/getStudentTrialExamsByTeacher")
    public ResponseEntity<?> getStudentTrialExamsByTeacher(@RequestBody GetStudentTrialExamsByTeacherDTO request){
        List<GetStudentTrialExamsDTO> studentTrialExams = trialExamService.getAllTrialExamsByStudentId(request.getStudent_id());

        if (studentTrialExams.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(studentTrialExams, HttpStatus.OK);
    }

}
