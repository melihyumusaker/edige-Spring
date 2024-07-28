package com.alibou.alibou.Service;

import com.alibou.alibou.Core.IServices.IMeetingService;
import com.alibou.alibou.DTO.Meeting.*;
import com.alibou.alibou.DTO.TrialExam.GetIsShownNumberDTO;
import com.alibou.alibou.Model.*;
import com.alibou.alibou.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class MeetingService implements IMeetingService {
    private final MeetingRepository meetingRepository;
    private final RelationRepository relationRepository;
    private final NotificationStudentRepository notificationStudentRepository;

    @Autowired
    public MeetingService(MeetingRepository meetingRepository, RelationRepository relationRepository, NotificationStudentRepository notificationStudentRepository){
        this.meetingRepository = meetingRepository;
        this.relationRepository = relationRepository;
        this.notificationStudentRepository = notificationStudentRepository;
    }


    public boolean createMeeting(CreateMeetingDTO request) {
        // Önce ilişki var mı kontrol edelim
        Optional<Relation> relationOptional = relationRepository.findRelationByStudentIdAndTeacherId(request.getStudent_id(), request.getTeacher_id());

        if (relationOptional.isPresent()) {
            Relation relation = relationOptional.get();

            Meeting newMeeting = Meeting.builder()
                    .relation_id(relationOptional.get())
                    .description(request.getDescription())
                    .start_day(request.getStart_day())
                    .start_hour(request.getStart_hour())
                    .location(request.getLocation())
                    .title(request.getTitle())
                    .createdAt(LocalDateTime.now())
                    .is_shown(0)
                    .build();

            meetingRepository.save(newMeeting);

            NotificationStudent notificationStudent = NotificationStudent.builder().
                    student_id(relationOptional.get().getStudent_id())
                    .message("Yeni Toplantı Eklendi")
                    .create_at(java.sql.Date.valueOf(LocalDate.now()))
                    .is_shown(false).build();
            notificationStudentRepository.save(notificationStudent);

            return true;
        }
        return false;
    }


    public boolean updateMeeting(UpdateMeetingDTO request) {
        Optional<Meeting> optionalMeeting = meetingRepository.findById(request.getMeeting_id());

        if (optionalMeeting.isPresent()) {
            Meeting meeting = optionalMeeting.get();

            Map<String, Object> updates = new HashMap<>();
            updates.put("isStudentJoin", request.getIs_student_join());
            updates.put("isParentJoin", request.getIs_parent_join());
            updates.put("description", request.getDescription());
            updates.put("startDay", request.getStart_day());
            updates.put("startHour", request.getStart_hour());
            updates.put("title", request.getTitle());
            updates.put("location", request.getLocation());
            updates.put("teacherComment" , request.getTeacher_comment());

            updates.forEach((field, value) -> {
                if (value != null) {
                    switch (field) {
                        case "isStudentJoin":
                            meeting.setIs_student_join((Integer) value);
                            break;
                        case "isParentJoin":
                            meeting.setIs_parent_join((Integer) value);
                            break;
                        case "description":
                            meeting.setDescription((String) value);
                            break;
                        case "startDay":
                            meeting.setStart_day((LocalDate) value);
                            break;
                        case "startHour":
                            meeting.setStart_hour((LocalTime) value);
                            break;
                        case "title":
                            meeting.setTitle((String) value);
                            break;
                        case "location":
                            meeting.setLocation((String) value);
                            break;
                        case "teacherComment":
                            meeting.setTeacher_comment((String) value);
                    }
                }
            });
            meetingRepository.save(meeting);

            NotificationStudent notificationStudent = NotificationStudent.builder().
                    student_id(optionalMeeting.get().getRelation_id().getStudent_id())
                    .message(meeting.getTitle() + " Adlı Toplantın Güncellendi")
                    .create_at(java.sql.Date.valueOf(LocalDate.now()))
                    .is_shown(false).build();
            notificationStudentRepository.save(notificationStudent);

            return true;
        }
        return false;
    }

    @Override
    public void deleteMeeting(DeleteMeetingDTO request) {
        int meeting_id = request.getMeeting_id();

        Optional<Meeting> optionalMeeting = meetingRepository.findById(meeting_id);

        if(optionalMeeting.isPresent()){
            meetingRepository.deleteById(meeting_id);
        }else{
            throw new NoSuchElementException("Toplantı bulunamadı.");
        }

    }

    @Override
    public List<Meeting> getStudentMeetings(GetStudentMeetingsDTO request) {
        int teacherId = request.getTeacher_id();
        int studentId = request.getStudent_id();

        Optional<Relation> optionalRelation = relationRepository.findRelationByStudentIdAndTeacherId(studentId , teacherId);

        if (optionalRelation.isPresent()) {
            return meetingRepository.findByRelationId(optionalRelation.get().getRelation_id());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Meeting> getTeacherAllMeetings(GetTeacherMeetingsDTO request) {
        int teacherId = request.getTeacher_id();

        List<Integer> relationIds  = relationRepository.findRelationIdsByTeacherId(teacherId);
        if (!relationIds.isEmpty()) {
            return meetingRepository.findByRelationIds(relationIds);
        } else {
            return Collections.emptyList();
        }
    }
    @Override
    public int countUnshownMeetingByStudentId(GetIsShownNumberDTO request) {
        try {
            return meetingRepository.countByStudentIdAndIsShownFalse(request.getStudent_id());
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while counting unshown meetings: " + ex.getMessage());
        }
    }

    @Transactional
    public void updateIsShown() {
        LocalDateTime twoDaysAgo = LocalDateTime.now().minusDays(2);
        List<Meeting> meetings = meetingRepository.findMeetingsToBeShown(twoDaysAgo);
        for (Meeting meeting : meetings) {
            meeting.setIs_shown(1);
        }
        meetingRepository.saveAll(meetings);
    }

    @Override
    @Transactional
    public void deletePastMeetings() {
        meetingRepository.deleteMeetingsOlderThan30Days();
    }


}
