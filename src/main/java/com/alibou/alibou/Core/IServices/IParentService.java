package com.alibou.alibou.Core.IServices;

import com.alibou.alibou.DTO.Parent.GetParentIdByUserIdDTO;
import com.alibou.alibou.Model.Parent;

import java.util.List;

public interface IParentService {
    List<Parent> getAllParents();
    int getParentIdByUserId(GetParentIdByUserIdDTO request);
}
