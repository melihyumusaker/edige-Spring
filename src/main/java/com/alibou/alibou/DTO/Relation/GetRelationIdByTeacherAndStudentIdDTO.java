package com.alibou.alibou.DTO.Relation;

import lombok.Data;

@Data
public class GetRelationIdByTeacherAndStudentIdDTO {
    private int student_id;
    private int teacher_id;
}
