package com.alibou.alibou.DTO.QR;

import com.alibou.alibou.Model.StudentRecord;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class GetStudentRecordsResponseDTO {
    private int toplamDevamsizlik;
    private List<SpecialStudentRecordDTO> studentRecord;

    @Data
    @Builder
    public static class SpecialStudentRecordDTO {
        private Date date;
        private LocalTime entry_time;
        private Date exit_time;
        private String status;
    }

}

