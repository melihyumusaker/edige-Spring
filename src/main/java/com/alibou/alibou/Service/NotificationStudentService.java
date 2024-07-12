package com.alibou.alibou.Service;

import com.alibou.alibou.Core.IServices.INotificationStudent;
import com.alibou.alibou.DTO.NotificationStudent.GetNotifStudentByStudentIdDTO;
import com.alibou.alibou.DTO.NotificationStudent.GetNotifStudentByStudentIdResponseDTO;
import com.alibou.alibou.Model.NotificationStudent;
import com.alibou.alibou.Repository.NotificationStudentRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationStudentService implements INotificationStudent {
    private final NotificationStudentRepository notificationStudentRepository;

    @Autowired
    public NotificationStudentService(NotificationStudentRepository notificationStudentRepository){
        this.notificationStudentRepository = notificationStudentRepository;
    }


    @Override
    public List<GetNotifStudentByStudentIdResponseDTO> getNotifStudentByStudentId(GetNotifStudentByStudentIdDTO request) {
        List<NotificationStudent> notifs = notificationStudentRepository.findAllByStudentId(request.getStudent_id());
        return notifs.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public int getUnseenNotifNumber(GetNotifStudentByStudentIdDTO request) {
        int unseenNotifNumber = notificationStudentRepository.findUnseenNotifNumber(request.getStudent_id());
        return unseenNotifNumber;
    }

    @Override
    public void setAllNotifsUnshownValue1(GetNotifStudentByStudentIdDTO request) {
        notificationStudentRepository.setAllNotifsUnshownValue1(request.getStudent_id());
    }

    private GetNotifStudentByStudentIdResponseDTO convertToDto(NotificationStudent notificationStudent) {
        GetNotifStudentByStudentIdResponseDTO dto = new GetNotifStudentByStudentIdResponseDTO();
        dto.setMessage(notificationStudent.getMessage());
        dto.setCreate_at(notificationStudent.getCreate_at());
        dto.setIs_shown(notificationStudent.getIs_shown());
        return dto;
    }
}
