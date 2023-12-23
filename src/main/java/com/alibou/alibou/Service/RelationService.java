package com.alibou.alibou.Service;

import com.alibou.alibou.Core.IServices.IRelationService;
import com.alibou.alibou.DTO.Relation.GetRelationIdByTeacherAndStudentIdDTO;
import com.alibou.alibou.Model.Relation;
import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Repository.RelationRepository;
import com.alibou.alibou.Repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RelationService implements IRelationService {
    private final RelationRepository relationRepository;

    @Autowired
    public RelationService(RelationRepository relationRepository){
        this.relationRepository = relationRepository;
    }

    public List<Relation> getAllRelations(){
        return relationRepository.findAll();
    }

    public int getRelationIdByTeacherAndStudentId(GetRelationIdByTeacherAndStudentIdDTO request){

        int studentId = request.getStudent_id();
        int teacherId = request.getTeacher_id();

        // RelationRepository içerisinde özel bir sorgu yaparak istenen ilişkiyi bulma
        Optional<Relation> relation = relationRepository.findRelationByStudentIdAndTeacherId(studentId, teacherId);

        // Eğer relation varsa, relation_id'yi döndür, yoksa -1 döndür veya hata fırlat
        return relation.map(Relation::getRelation_id).orElse(-1);
    }
}
