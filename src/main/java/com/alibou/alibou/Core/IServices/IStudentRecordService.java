package com.alibou.alibou.Core.IServices;

import com.alibou.alibou.DTO.QR.GetStudentRecordsDTO;
import com.alibou.alibou.DTO.QR.GetStudentRecordsResponseDTO;
import com.alibou.alibou.DTO.QR.SaveStudentRecordsDTO;
import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Model.StudentRecord;

import java.util.List;

public interface IStudentRecordService {
    void saveStudentRecords(SaveStudentRecordsDTO request);
    GetStudentRecordsResponseDTO getStudentRecords(GetStudentRecordsDTO request);

}
