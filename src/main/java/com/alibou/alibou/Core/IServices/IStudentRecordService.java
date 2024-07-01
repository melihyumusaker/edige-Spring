package com.alibou.alibou.Core.IServices;

import com.alibou.alibou.DTO.QR.GetStudentRecordsDTO;
import com.alibou.alibou.DTO.QR.GetStudentRecordsResponseDTO;
import com.alibou.alibou.DTO.QR.SaveStudentRecordsDTO;

import java.util.List;

public interface IStudentRecordService {
    String saveStudentRecords(SaveStudentRecordsDTO request);
    GetStudentRecordsResponseDTO getStudentRecords(GetStudentRecordsDTO request);
    int getStudentTotalRecord(GetStudentRecordsDTO request);
}
