package com.alibou.alibou.Service;

import com.alibou.alibou.Core.IServices.IMeetingService;
import com.alibou.alibou.DTO.Meeting.CreateMeetingDTO;
import com.alibou.alibou.Model.Meeting;
import com.alibou.alibou.Model.Relation;
import com.alibou.alibou.Repository.MeetingRepository;
import com.alibou.alibou.Repository.RelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        LocalDate date = request.getDate();

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
            // Relation bulunamadı, Meeting oluşturma başarısız
            return false;
        }
    }
}
