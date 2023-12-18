package com.alibou.alibou.Controller;

import com.alibou.alibou.Core.IServices.IWeeklyProgramService;
import com.alibou.alibou.Model.Teacher;
import com.alibou.alibou.Model.WeeklyProgram;
import com.alibou.alibou.Service.TeacherService;
import com.alibou.alibou.Service.WeeklyProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
