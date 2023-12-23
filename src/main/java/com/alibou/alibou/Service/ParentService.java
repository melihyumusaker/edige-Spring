package com.alibou.alibou.Service;

import com.alibou.alibou.Core.IServices.IParentService;
import com.alibou.alibou.DTO.Parent.GetParentIdByUserIdDTO;
import com.alibou.alibou.Model.Parent;
import com.alibou.alibou.Model.Teacher;
import com.alibou.alibou.Repository.ParentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentService implements IParentService {
    private final ParentRepository parentRepository;

    @Autowired
    public ParentService(ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    public List<Parent> getAllParents() {
        return parentRepository.findAll();
    }

    @Override
    public int getParentIdByUserId(GetParentIdByUserIdDTO request) {
        Parent parent = parentRepository.findByUserId(request.getUser_id())
                .orElseThrow(() -> new EntityNotFoundException("Aile bulunamadı: " + request.getUser_id()));

        return parent.getParent_id();
    }


}
