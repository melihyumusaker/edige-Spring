package com.alibou.alibou.Core.IServices;

import com.alibou.alibou.DTO.Relation.GetRelationIdByTeacherAndStudentIdDTO;
import com.alibou.alibou.Model.Relation;

import java.util.List;

public interface IRelationService {
    List<Relation> getAllRelations();

    int getRelationIdByTeacherAndStudentId(GetRelationIdByTeacherAndStudentIdDTO request);
}
