package com.alibou.alibou.DTO.Meeting;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CreateMeetingDTO {
    private int relation_id;
    private LocalDateTime date;
}
