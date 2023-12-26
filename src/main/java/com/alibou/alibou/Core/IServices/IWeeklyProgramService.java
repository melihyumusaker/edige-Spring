package com.alibou.alibou.Core.IServices;
import com.alibou.alibou.DTO.WeeklyProgram.*;
import com.alibou.alibou.Model.Parent;
import com.alibou.alibou.Model.WeeklyProgram;
import java.util.List;
import java.util.Optional;

public interface IWeeklyProgramService {
    List<WeeklyProgram> getAllWeeklyPrograms();
    boolean createWeeklyProgram(CreateWeeklyProgramDTO request);
    boolean updateWeeklyProgram(UpdateWeeklyProgramDTO request);
    List<WeeklyProgram> getWeeklyProgramByStudentId(GetWeeklyProgramByStudentIdWithRequestBodyDTO request);
    Optional<List<WeeklyProgramDetailsDTO>> findStudentWeeklyProgramByParentId(GetParentChildWeeklyProgramDTO request);
}
