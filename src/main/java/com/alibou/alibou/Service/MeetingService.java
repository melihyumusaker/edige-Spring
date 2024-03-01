package com.alibou.alibou.Service;

import com.alibou.alibou.Core.IServices.IMeetingService;
import com.alibou.alibou.DTO.Meeting.CreateMeetingDTO;
import com.alibou.alibou.DTO.Meeting.UpdateMeetingDTO;
import com.alibou.alibou.Model.Meeting;
import com.alibou.alibou.Model.Relation;
import com.alibou.alibou.Repository.MeetingRepository;
import com.alibou.alibou.Repository.RelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public boolean createMeeting(CreateMeetingDTO request){
        int relationId = request.getRelation_id();
        LocalDateTime date = request.getDate();

        // Öncelikle, verilen relation_id ile ilgili Relation var mı kontrol edelim
        Optional<Relation> relation = relationRepository.findById(relationId);

        if (relation.isPresent()) {
            // Relation bulundu, Meeting oluşturalım
            Meeting newMeeting = Meeting.builder()
                    .relation_id(relation.get())
                    .date(date)
                    .build();

            // Meeting'i kaydedelim
            meetingRepository.save(newMeeting);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateMeeting(UpdateMeetingDTO request){
        int meetingId = request.getMeeting_id();
        String teacherComment = request.getTeacher_comment();
        Integer isStudentJoin = request.getIs_student_join();
        Integer isParentJoin = request.getIs_parent_join();

        Optional<Meeting> optionalMeeting = meetingRepository.findById(meetingId);

        if (optionalMeeting.isPresent()) {
            Meeting existingMeeting = optionalMeeting.get();

            // Yeni bilgilerle toplantıyı güncelle
            if (teacherComment != null) {
                existingMeeting.setTeacher_comment(teacherComment);
            }
            if (isStudentJoin != null) {
                existingMeeting.setIs_student_join(isStudentJoin);
            }
            if (isParentJoin != null) {
                existingMeeting.setIs_parent_join(isParentJoin);
            }

            meetingRepository.save(existingMeeting);
            return true; // Başarılı güncelleme
        } else {
            return false; // Toplantı bulunamadı
        }
    }
}
