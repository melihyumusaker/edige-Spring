package com.alibou.alibou.Controller;

import com.alibou.alibou.Core.IServices.INotificationStudent;
import com.alibou.alibou.DTO.NotificationStudent.GetNotifStudentByStudentIdDTO;
import com.alibou.alibou.DTO.NotificationStudent.GetNotifStudentByStudentIdResponseDTO;
import com.alibou.alibou.DTO.TrialExam.GetStudentTrialExamsByTeacherDTO;
import com.alibou.alibou.DTO.TrialExam.GetStudentTrialExamsDTO;
import com.alibou.alibou.Model.Meeting;
import com.alibou.alibou.Model.NotificationStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/notifStudent")
public class NotificationStudentController {
    private final INotificationStudent notificationStudentService;

    @Autowired
    public NotificationStudentController(INotificationStudent notificationStudentService){
     this.notificationStudentService = notificationStudentService;
    }

    @PostMapping(path = "/getNotifStudentByStudentId", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getNotifStudentByStudentId(@RequestBody GetNotifStudentByStudentIdDTO request){
        try{
            List<GetNotifStudentByStudentIdResponseDTO> notifs = notificationStudentService.getNotifStudentByStudentId(request);
            Collections.reverse(notifs);
            return ResponseEntity.ok(notifs);
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping(path = "/getUnseenNotifNumber", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getUnseenNotifNumber(@RequestBody GetNotifStudentByStudentIdDTO request){
        try{
            int unseenNotifNumber= notificationStudentService.getUnseenNotifNumber(request);
            return ResponseEntity.ok(unseenNotifNumber);
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping(path = "/setAllNotifsUnshownValue1", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> setAllNotifsUnshownValue1(@RequestBody GetNotifStudentByStudentIdDTO request){
        try{
            notificationStudentService.setAllNotifsUnshownValue1(request);
            return ResponseEntity.ok("İşlem Başarılı");
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
