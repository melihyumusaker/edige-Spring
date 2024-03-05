package com.alibou.alibou.Service;

import com.alibou.alibou.Core.IServices.IUserService;
import com.alibou.alibou.DTO.User.KullaniciDTO;
import com.alibou.alibou.DTO.User.UserUpdateDTO;
import com.alibou.alibou.Model.User;
import com.alibou.alibou.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<KullaniciDTO> findAllActiveUsers() {
        List<User> users = userRepository.findAllActiveUsers();
        List<KullaniciDTO> kullaniciDTOList = new ArrayList<>();
        for (User user : users) {
            kullaniciDTOList.add(mapToKullaniciDTO(user));
        }
        return kullaniciDTOList;
    }

    private KullaniciDTO mapToKullaniciDTO(User user) {
        return KullaniciDTO.builder()
                .user_id(user.getUserId())
                .username(user.getUsername())
                .password(user.getPassword())
                .name(user.getName())
                .surname(user.getSurname())
                .birth_date(user.getBirth_date())
                .email(user.getEmail())
                .phone(user.getPhone())
                .city(user.getCity())
                .role(user.getRole())
                .is_active(user.getIs_active())
                .build();
    }


    public void deactivateUserById(int userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isPresent()){
            userRepository.deactivateUserById(userId);
        }

    }

    public User updateUser(int userId, KullaniciDTO updatedUserDetails) {
        User userToUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

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
        if(updatedUserDetails.getIs_active() != null){
            userToUpdate.setIs_active(updatedUserDetails.getIs_active());
        }
        if(updatedUserDetails.getPassword() != null){
            userToUpdate.setPassword(passwordEncoder.encode(updatedUserDetails.getPassword()) );
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
