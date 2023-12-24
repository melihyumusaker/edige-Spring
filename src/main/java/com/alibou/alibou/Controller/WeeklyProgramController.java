package com.alibou.alibou.Controller;

import com.alibou.alibou.Core.IServices.IWeeklyProgramService;
import com.alibou.alibou.DTO.WeeklyProgram.CreateWeeklyProgramDTO;
import com.alibou.alibou.DTO.WeeklyProgram.UpdateWeeklyProgramDTO;
import com.alibou.alibou.Model.Teacher;
import com.alibou.alibou.Model.WeeklyProgram;
import com.alibou.alibou.Service.TeacherService;
import com.alibou.alibou.Service.WeeklyProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weekly-programs")
public class WeeklyProgramController {
    private final IWeeklyProgramService weeklyProgramService;

    @Autowired
    public WeeklyProgramController(IWeeklyProgramService weeklyProgramService) {
        this.weeklyProgramService = weeklyProgramService;
    }

    @GetMapping("/all")
    public List<WeeklyProgram> getAllTeachers() {
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

}
