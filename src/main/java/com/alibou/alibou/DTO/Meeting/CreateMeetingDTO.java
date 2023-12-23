package com.alibou.alibou.DTO.Meeting;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateMeetingDTO {
    private int relation_id;
    private LocalDate date;
}
