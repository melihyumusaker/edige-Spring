package com.alibou.alibou.Core.IServices;

import com.alibou.alibou.DTO.TrialExam.DeleteTrialExamDTO;
import com.alibou.alibou.DTO.TrialExam.GetStudentTrialExamsDTO;
import com.alibou.alibou.DTO.TrialExam.SetTrialExamDTO;
import com.alibou.alibou.Model.TrialExam;

import java.util.List;

public interface ITrialExamService {
    List<TrialExam> getAllTrialExams();
    boolean saveStudentTrialExamResult(SetTrialExamDTO request);
    List<GetStudentTrialExamsDTO> getAllTrialExamsByStudentId(int studentId);

    void deleteTrialExam(DeleteTrialExamDTO request);
}
