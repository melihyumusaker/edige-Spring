package com.alibou.alibou.Service;

import com.alibou.alibou.Core.IServices.IMeetingService;
import com.alibou.alibou.DTO.Meeting.*;
import com.alibou.alibou.Model.Meeting;
import com.alibou.alibou.Model.Relation;
import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Model.Teacher;
import com.alibou.alibou.Repository.MeetingRepository;
import com.alibou.alibou.Repository.RelationRepository;
import com.alibou.alibou.Repository.StudentRepository;
import com.alibou.alibou.Repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class MeetingService implements IMeetingService {
    private final MeetingRepository meetingRepository;
    private final RelationRepository relationRepository;

    @Autowired
    public MeetingService(MeetingRepository meetingRepository, RelationRepository relationRepository){
        this.meetingRepository = meetingRepository;
        this.relationRepository = relationRepository;
    }

    public List<Meeting> getAllMeetings(){
        return meetingRepository.findAll();
    }

    public boolean createMeeting(CreateMeetingDTO request) {
        // Önce ilişki var mı kontrol edelim
        Optional<Relation> relationOptional = relationRepository.findRelationByStudentIdAndTeacherId(request.getStudent_id(), request.getTeacher_id());

        if (relationOptional.isPresent()) {
            Relation relation = relationOptional.get();
            // Relation bulundu, Meeting oluşturalım
            Meeting newMeeting = Meeting.builder()
                    .relation_id(relationOptional.get()) // Relation nesnesinden gelen ID
                    .description(request.getDescription()) // DTO'dan gelen description
                    .start_day(request.getStart_day()) // DTO'dan gelen startDay
                    .start_hour(request.getStart_hour()) // DTO'dan gelen startHour
                    .location(request.getLocation()) // DTO'dan gelen location
                    .title(request.getTitle())
                    .createdAt(LocalDateTime.now())
                    .build();

            // Meeting'i kaydedelim
            meetingRepository.save(newMeeting);
            return true; // Başarılı bir şekilde oluşturuldu ve kaydedildi
        }
        return false; // Relation bulunamadıysa false dön
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

            // Apply updates only if the value is not null
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
                    }
                }
            });

            meetingRepository.save(meeting);
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
            // Relation bulunduğunda, bu relation'a ait meeting'leri döndür
            return meetingRepository.findByRelationId(optionalRelation.get().getRelation_id());
        } else {
            // Eğer relation bulunamazsa boş bir liste döndür
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
            // Eğer hiç relation_id yoksa, boş bir liste döndürüyoruz.
            return Collections.emptyList();
        }

    }


}
