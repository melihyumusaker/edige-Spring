package com.alibou.alibou.Core.IServices;

import com.alibou.alibou.Model.User;

import java.util.List;

public interface IUserService {
    List<User> getAllUsers();

    int getUserIdByEmail(String userEmail);
}
