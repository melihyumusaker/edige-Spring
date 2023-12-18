package com.alibou.alibou.Service;

import com.alibou.alibou.Core.IServices.IUserService;
import com.alibou.alibou.Model.User;
import com.alibou.alibou.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public int getUserIdByEmail(String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);

        if(user.isPresent()){
            return user.get().getUser_id();
        }
        else{
            throw new EntityNotFoundException("Kullanici bulunamadi");
        }

    }
}
