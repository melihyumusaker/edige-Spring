package com.alibou.alibou.Service;

import com.alibou.alibou.Core.IServices.IParentService;
import com.alibou.alibou.Model.Parent;
import com.alibou.alibou.Repository.ParentRepository;
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


}
