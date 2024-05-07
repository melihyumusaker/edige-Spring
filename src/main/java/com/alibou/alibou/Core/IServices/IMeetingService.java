package com.alibou.alibou.Core.IServices;

import com.alibou.alibou.DTO.Meeting.*;
import com.alibou.alibou.Model.Meeting;

import java.util.List;

public interface IMeetingService {
    List<Meeting> getAllMeetings();
    boolean createMeeting(CreateMeetingDTO request);
    boolean updateMeeting(UpdateMeetingDTO request);
    void deleteMeeting(DeleteMeetingDTO request);

    List<Meeting> getStudentMeetings(GetStudentMeetingsDTO request);

    List<Meeting> getTeacherAllMeetings(GetTeacherMeetingsDTO request);
}
