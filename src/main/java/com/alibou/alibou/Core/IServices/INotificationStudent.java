package com.alibou.alibou.Core.IServices;

import com.alibou.alibou.DTO.NotificationStudent.GetNotifStudentByStudentIdDTO;
import com.alibou.alibou.DTO.NotificationStudent.GetNotifStudentByStudentIdResponseDTO;
import com.alibou.alibou.Model.NotificationStudent;

import java.util.List;

public interface INotificationStudent {
    List<GetNotifStudentByStudentIdResponseDTO> getNotifStudentByStudentId(GetNotifStudentByStudentIdDTO request);

    int getUnseenNotifNumber(GetNotifStudentByStudentIdDTO request);

    void setAllNotifsUnshownValue1(GetNotifStudentByStudentIdDTO request);
}
