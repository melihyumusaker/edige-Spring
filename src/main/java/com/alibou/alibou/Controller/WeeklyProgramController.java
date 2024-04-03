package com.alibou.alibou.Controller;

import com.alibou.alibou.Core.IServices.IParentService;
import com.alibou.alibou.Core.IServices.IStudentService;
import com.alibou.alibou.Core.IServices.IWeeklyProgramService;
import com.alibou.alibou.DTO.WeeklyProgram.*;
import com.alibou.alibou.Model.Parent;
import com.alibou.alibou.Model.Teacher;
import com.alibou.alibou.Model.WeeklyProgram;
import com.alibou.alibou.Service.TeacherService;
import com.alibou.alibou.Service.WeeklyProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/weekly-programs")
public class WeeklyProgramController {
    private final IWeeklyProgramService weeklyProgramService;
    private final IParentService parentService;
    private final IStudentService studentService;

    @Autowired
    public WeeklyProgramController(IWeeklyProgramService weeklyProgramService,IParentService parentService,IStudentService studentService) {
        this.weeklyProgramService = weeklyProgramService;
        this.parentService = parentService;
        this.studentService = studentService;
    }

    @GetMapping("/all")
    public List<WeeklyProgram> getAllWeeklyPrograms() {
        return weeklyProgramService.getAllWeeklyPrograms();
    }

    @PostMapping("/createWeeklyProgram")
    public ResponseEntity<?> createWeeklyProgram(@RequestBody CreateWeeklyProgramDTO request){
        boolean isCreated = weeklyProgramService.createWeeklyProgram(request);

        if (isCreated) {
            return ResponseEntity.ok("");
        } else {
            return ResponseEntity.badRequest().body("Student not found or invalid data");
        }

    }

    @PutMapping("/updateWeeklyProgram")
    public ResponseEntity<String> updateWeeklyProgram(@RequestBody UpdateWeeklyProgramDTO request){
        boolean isUpdated = weeklyProgramService.updateWeeklyProgram(request);

        if (isUpdated) {
            return ResponseEntity.ok("");
        } else {
            return ResponseEntity.badRequest().body("");
        }
    }

    @PostMapping(path = "/getWeeklyProgramByStudentId", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getWeeklyProgramByStudentIdWithRequestBody(@RequestBody GetWeeklyProgramByStudentIdWithRequestBodyDTO request) {
        List<WeeklyProgram> studentWeeklyProgram = weeklyProgramService.getWeeklyProgramByStudentId(request);

        if (studentWeeklyProgram.isEmpty()) {
            return ResponseEntity.badRequest().body("No weekly program found for the given student ID");
        } else {
            return ResponseEntity.ok(studentWeeklyProgram);
        }
    }

    @PostMapping(path = "/getParentChildWeeklyProgram", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getParentChildWeeklyProgram(@RequestBody GetParentChildWeeklyProgramDTO request) {
        Optional<List<WeeklyProgramDetailsDTO>> weeklyProgramDetails = weeklyProgramService.findStudentWeeklyProgramByParentId(request);

        if (weeklyProgramDetails.isPresent()) {
            return ResponseEntity.ok(weeklyProgramDetails.get());
        } else {
            return ResponseEntity.badRequest().body("No weekly program found for the given parent ID");
        }
    }

    @DeleteMapping("/deleteWeeklyProgram")
    public ResponseEntity<?> deleteWeeklyProgram(@RequestBody DeleteWeeklyProgramDTO request) {
        try {
            weeklyProgramService.deleteWeeklyProgram(request);
            return ResponseEntity.ok("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Haftalık programı silerken bir hata oluştu: " + e.getMessage());
        }
    }

}
