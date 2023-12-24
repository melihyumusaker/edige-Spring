package com.alibou.alibou.Core.IServices;
import com.alibou.alibou.DTO.WeeklyProgram.CreateWeeklyProgramDTO;
import com.alibou.alibou.DTO.WeeklyProgram.UpdateWeeklyProgramDTO;
import com.alibou.alibou.Model.WeeklyProgram;
import java.util.List;

public interface IWeeklyProgramService {
    List<WeeklyProgram> getAllWeeklyPrograms();
    boolean createWeeklyProgram(CreateWeeklyProgramDTO request);
    boolean updateWeeklyProgram(UpdateWeeklyProgramDTO request);
}
