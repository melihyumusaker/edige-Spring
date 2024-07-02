package com.alibou.alibou.DTO.NotificationStudent;

import com.alibou.alibou.Model.Student;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
public class GetNotifStudentByStudentIdResponseDTO {
    private String message;
    private Date create_at;
    private Boolean is_shown;
}
