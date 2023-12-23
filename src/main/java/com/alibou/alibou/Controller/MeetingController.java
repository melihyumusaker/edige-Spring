package com.alibou.alibou.Controller;
import com.alibou.alibou.Core.IServices.IMeetingService;
import com.alibou.alibou.DTO.Meeting.CreateMeetingDTO;
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

    @GetMapping("/all")
    public List<Meeting> getAllStudents() {
        return meetingService.getAllMeetings();
    }

    @PostMapping("/createMeeting")
    public ResponseEntity<String> createMeeting(@RequestBody CreateMeetingDTO request){
        boolean isCreated = meetingService.createMeeting(request);

        if (isCreated) {
            return ResponseEntity.ok("Meeting created successfully");
        } else {
            return ResponseEntity.badRequest().body("Meeting creation failed");
        }
    }

}
