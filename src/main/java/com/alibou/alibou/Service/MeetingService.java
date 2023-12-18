package com.alibou.alibou.Service;

import com.alibou.alibou.Core.IServices.IMeetingService;
import com.alibou.alibou.Model.Meeting;
import com.alibou.alibou.Repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingService implements IMeetingService {
    private final MeetingRepository meetingRepository;

    @Autowired
    public MeetingService(MeetingRepository meetingRepository){
        this.meetingRepository = meetingRepository;
    }

    public List<Meeting> getAllMeetings(){
        return meetingRepository.findAll();
    }
}
