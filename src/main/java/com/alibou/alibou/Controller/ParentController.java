package com.alibou.alibou.Controller;

import com.alibou.alibou.Core.IServices.IParentService;
import com.alibou.alibou.Model.Parent;
import com.alibou.alibou.Service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/parents")
public class ParentController {
    private final IParentService parentService;

    @Autowired
    public ParentController(IParentService parentService) {
        this.parentService = parentService;
    }

    @GetMapping("/all")
    public List<Parent> getAllParents() {
        return parentService.getAllParents();
    }

}
