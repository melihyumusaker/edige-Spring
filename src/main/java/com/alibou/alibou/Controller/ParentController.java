package com.alibou.alibou.Controller;

import com.alibou.alibou.Core.IServices.IParentService;
import com.alibou.alibou.DTO.Parent.GetParentIdByUserIdDTO;
import com.alibou.alibou.Model.Parent;
import com.alibou.alibou.Service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    // user_id'si verilen parent'in parent_id'sini dönme
    @GetMapping("/getParentIdByUserId")
    public ResponseEntity<?> getParentIdByUserId(@RequestBody GetParentIdByUserIdDTO request) {
        try {
            int parent_id = parentService.getParentIdByUserId(request);
            return ResponseEntity.ok(parent_id);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
