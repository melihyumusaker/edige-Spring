package com.alibou.alibou.Controller;
import com.alibou.alibou.Core.IServices.IMeetingService;
import com.alibou.alibou.Model.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
