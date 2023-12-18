package com.alibou.alibou.Service;

import com.alibou.alibou.Core.IServices.IRelationService;
import com.alibou.alibou.Model.Relation;
import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Repository.RelationRepository;
import com.alibou.alibou.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
