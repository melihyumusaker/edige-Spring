package com.alibou.alibou.Repository;

import com.alibou.alibou.Model.Relation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RelationRepository extends JpaRepository<Relation , Integer> {

    @Query("SELECT r FROM Relation r WHERE r.student_id.student_id = :studentId AND r.teacher_id.teacher_id = :teacherId")
    Optional<Relation> findRelationByStudentIdAndTeacherId(@Param("studentId") int studentId, @Param("teacherId") int teacherId);

    @Query("SELECT r.relation_id FROM Relation r WHERE r.teacher_id.teacher_id = :teacherId")
    List<Integer> findRelationIdsByTeacherId(@Param("teacherId") int teacherId);

}
