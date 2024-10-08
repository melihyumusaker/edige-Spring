package com.alibou.alibou.Core.IServices;

import com.alibou.alibou.DTO.User.KullaniciDTO;
import com.alibou.alibou.DTO.User.UserUpdateDTO;
import com.alibou.alibou.Model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getAllUsers();
    public List<KullaniciDTO> findAllActiveUsers();
    int getUserIdByEmail(String userEmail);
    void deactivateUserById(int userId);
    User updateUser(int userId, KullaniciDTO updatedUserDetails);
}
