package com.alibou.alibou.Controller;

import com.alibou.alibou.Core.IServices.IRelationService;
import com.alibou.alibou.Model.Relation;
import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Service.RelationService;
import com.alibou.alibou.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
