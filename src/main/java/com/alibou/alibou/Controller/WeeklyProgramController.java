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
    public ResponseEntity<String> createWeeklyProgram(@RequestBody CreateWeeklyProgramDTO request){
        boolean isCreated = weeklyProgramService.createWeeklyProgram(request);

        if (isCreated) {
            return ResponseEntity.ok("Weekly program created successfully");
        } else {
            return ResponseEntity.badRequest().body("Student not found or invalid data");
        }
    }

    @PutMapping("/updateWeeklyProgram")
    public ResponseEntity<String> updateWeeklyProgram(@RequestBody UpdateWeeklyProgramDTO request){
        boolean isUpdated = weeklyProgramService.updateWeeklyProgram(request);

        if (isUpdated) {
            return ResponseEntity.ok("Weekly program updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Weekly program not found or invalid data");
        }
    }

    @PostMapping("/getWeeklyProgramByStudentId")
    public ResponseEntity<?> getWeeklyProgramByStudentIdWithRequestBody(@RequestBody GetWeeklyProgramByStudentIdWithRequestBodyDTO request) {
        List<WeeklyProgram> studentWeeklyProgram = weeklyProgramService.getWeeklyProgramByStudentId(request);

        if (studentWeeklyProgram.isEmpty()) {
            return ResponseEntity.badRequest().body("No weekly program found for the given student ID");
        } else {
            return ResponseEntity.ok(studentWeeklyProgram);
        }
    }

    // parent öğrencinin ders programını görüntüleyebilecek
    @PostMapping("/getParentChildWeeklyProgram")
    public ResponseEntity<?> getParentChildWeeklyProgram(@RequestBody GetParentChildWeeklyProgramDTO request) {
        Optional<List<WeeklyProgramDetailsDTO>> weeklyProgramDetails = weeklyProgramService.findStudentWeeklyProgramByParentId(request);

        if (weeklyProgramDetails.isPresent()) {
            return ResponseEntity.ok(weeklyProgramDetails.get());
        } else {
            return ResponseEntity.badRequest().body("No weekly program found for the given parent ID");
        }
    }

}
