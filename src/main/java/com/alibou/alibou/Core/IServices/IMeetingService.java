package com.alibou.alibou.Core.IServices;

import com.alibou.alibou.DTO.Meeting.CreateMeetingDTO;
import com.alibou.alibou.DTO.Meeting.UpdateMeetingDTO;
import com.alibou.alibou.Model.Meeting;

import java.util.List;

public interface IMeetingService {
    List<Meeting> getAllMeetings();
    boolean createMeeting(CreateMeetingDTO request);
    boolean updateMeeting(UpdateMeetingDTO request);
}
