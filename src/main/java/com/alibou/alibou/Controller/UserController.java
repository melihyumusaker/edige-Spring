package com.alibou.alibou.Controller;

import com.alibou.alibou.Core.IServices.IStudentService;
import com.alibou.alibou.Core.IServices.IUserService;
import com.alibou.alibou.DTO.User.UserUpdateDTO;
import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Model.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;


    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<?>getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/getAllActiveUsers")
    public ResponseEntity<?> getAllActiveUsers() {
        List<User> activeUsers = userService.findAllActiveUsers();
        return ResponseEntity.ok(activeUsers);
    }

    @PutMapping("/{userId}/deactivate")
    public ResponseEntity<String> deactivateUser(@PathVariable int userId) {
        try {
            userService.deactivateUserById(userId);
            return ResponseEntity.ok("Kullanıcı pasif hale getirildi.");
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kullanıcı bulunamadı.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Bir hata oluştu.");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser( @RequestBody UserUpdateDTO updatedUserDetails) {
        try {
            User updatedUser = userService.updateUser(updatedUserDetails.getUser_id(), updatedUserDetails);
            return ResponseEntity.ok("Güncelleme Başarılı");
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kullanıcı bulunamadı.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Bir hata oluştu.");
        }
    }

}
