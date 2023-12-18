package com.alibou.alibou.Core.IServices;

import com.alibou.alibou.DTO.TrialExam.SetTrialExamDTO;
import com.alibou.alibou.Model.TrialExam;

import java.util.List;

public interface ITrialExamService {
    List<TrialExam> getAllTrialExams();

    TrialExam saveStudentTrialExamResult(SetTrialExamDTO request);
}
