package com.alibou.alibou.Service;

import com.alibou.alibou.Core.IServices.IUserService;
import com.alibou.alibou.DTO.User.UserUpdateDTO;
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

    public List<User> findAllActiveUsers() {
        return userRepository.findAllActiveUsers();
    }

    public void deactivateUserById(int userId) {
        userRepository.deactivateUserById(userId);
    }

    public User updateUser(int userId, UserUpdateDTO updatedUserDetails) {
        User userToUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        // Güncelleme işlemleri
        if (updatedUserDetails.getName() != null) {
            userToUpdate.setName(updatedUserDetails.getName());
        }
        if (updatedUserDetails.getSurname() != null) {
            userToUpdate.setSurname(updatedUserDetails.getSurname());
        }
        if (updatedUserDetails.getEmail() != null) {
            userToUpdate.setEmail(updatedUserDetails.getEmail());
        }
        if (updatedUserDetails.getPhone() != null) {
            userToUpdate.setPhone(updatedUserDetails.getPhone());
        }
        if (updatedUserDetails.getCity() != null) {
            userToUpdate.setCity(updatedUserDetails.getCity());
        }
        if (updatedUserDetails.getUsername() != null) {
            userToUpdate.setUsername(updatedUserDetails.getUsername());
        }
        if (updatedUserDetails.getBirth_date() != null) {
            userToUpdate.setBirth_date(updatedUserDetails.getBirth_date());
        }
        if (updatedUserDetails.getRole() != null) {
            userToUpdate.setRole(updatedUserDetails.getRole());
        }

        return userRepository.save(userToUpdate);
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
