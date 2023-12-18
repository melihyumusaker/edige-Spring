package com.alibou.alibou.Controller;

import com.alibou.alibou.Core.IServices.ITrialExamService;
import com.alibou.alibou.DTO.TrialExam.SetTrialExamDTO;
import com.alibou.alibou.Model.TrialExam;
import com.alibou.alibou.Service.TrialExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trial-exams")
public class TrialExamController {
    private final ITrialExamService trialExamService;

    @Autowired
    public TrialExamController(ITrialExamService trialExamService) {
        this.trialExamService = trialExamService;
    }

    @GetMapping("/all")
    public List<TrialExam> getAllTrialExams() {
        return trialExamService.getAllTrialExams();
    }

    @PostMapping("/setStudentTrialExamResult")
    public ResponseEntity<String> setStudentTrialExamResult(@RequestBody SetTrialExamDTO request){
        TrialExam result = trialExamService.saveStudentTrialExamResult(request);
        if (result != null) {
            return ResponseEntity.ok("Trial exam result saved successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save trial exam result");
        }
    }

}
