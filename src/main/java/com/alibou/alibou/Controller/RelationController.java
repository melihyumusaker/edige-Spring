package com.alibou.alibou.Controller;

import com.alibou.alibou.Core.IServices.IRelationService;
import com.alibou.alibou.DTO.Relation.GetRelationIdByTeacherAndStudentIdDTO;
import com.alibou.alibou.Model.Relation;
import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Service.RelationService;
import com.alibou.alibou.Service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/relations")
public class RelationController {
    private final IRelationService relationService;

    @Autowired
    public RelationController(IRelationService relationService) {
        this.relationService = relationService;
    }

    @GetMapping("/all")
    public List<Relation> getAllStudents() {
        return relationService.getAllRelations();
    }

    @GetMapping("/getRelationIdByTeacherAndStudentId")
    public ResponseEntity<Integer> GetRelationIdByTeacherAndStudentId(@RequestBody GetRelationIdByTeacherAndStudentIdDTO request){{

        int relationId = relationService.getRelationIdByTeacherAndStudentId(request);
        if (relationId == -1) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(relationId);
    }
    }

}
