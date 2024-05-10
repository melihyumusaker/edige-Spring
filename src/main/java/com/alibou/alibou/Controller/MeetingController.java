package com.alibou.alibou.Controller;
import com.alibou.alibou.Core.IServices.IMeetingService;
import com.alibou.alibou.DTO.Meeting.*;
import com.alibou.alibou.Model.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meetings")
public class MeetingController {
    private final IMeetingService meetingService;

    @Autowired
    public MeetingController(IMeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @PostMapping(path = "/getStudentAndTeacherSpecialMeetings" ,  produces = "application/json;charset=UTF-8")
    public  ResponseEntity<?> getStudentAndTeacherSpecialMeetings(@RequestBody GetStudentMeetingsDTO request){
        try{
            List<Meeting> meetings = meetingService.getStudentMeetings(request);
            return ResponseEntity.ok(meetings);
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping(path = "/getTeacherAllMeetings" ,  produces = "application/json;charset=UTF-8")
    public  ResponseEntity<?> getTeacherAllMeetings(@RequestBody GetTeacherMeetingsDTO request){
        try{
            List<Meeting> meetings = meetingService.getTeacherAllMeetings(request);
            return ResponseEntity.ok(meetings);
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/createMeeting")
    public ResponseEntity<?> createMeeting(@RequestBody CreateMeetingDTO request){
        try{
            boolean isCreated = meetingService.createMeeting(request);

            if (isCreated) {
                return ResponseEntity.ok("Meeting created successfully");
            } else {
                return ResponseEntity.badRequest().body("Meeting creation failed");
            }
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/deleteMeeting")
    public ResponseEntity<?> deleteMeeting(@RequestBody DeleteMeetingDTO request){
        try{
            meetingService.deleteMeeting(request);
            return ResponseEntity.ok("Meeting has been deleted");

        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/updateMeeting")
    public ResponseEntity<?> updateMeeting(@RequestBody UpdateMeetingDTO request){
        try{
            boolean isUpdated = meetingService.updateMeeting(request);
            if (isUpdated) {
                return ResponseEntity.ok("Meeting updated successfully");
            } else {
                return ResponseEntity.badRequest().body("Meeting not found");
            }
        }catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
