package com.alibou.alibou.Service;

import com.alibou.alibou.Core.IServices.IWeeklyProgramService;
import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Model.WeeklyProgram;
import com.alibou.alibou.Repository.StudentRepository;
import com.alibou.alibou.Repository.WeeklyProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeeklyProgramService implements IWeeklyProgramService {
    private final WeeklyProgramRepository weeklyProgramRepository;

    @Autowired
    public WeeklyProgramService(WeeklyProgramRepository weeklyProgramRepository){
        this.weeklyProgramRepository = weeklyProgramRepository;
    }

    public List<WeeklyProgram> getAllWeeklyPrograms(){
        return weeklyProgramRepository.findAll();
    }

}
