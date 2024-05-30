package com.alibou.alibou.DTO.QR;

import lombok.Data;

import java.util.Date;

@Data
public class SpecialStudentRecordDTO {
    private Date date;
    private Date entry_time;
    private Date exit_time;
    private String status;
}
