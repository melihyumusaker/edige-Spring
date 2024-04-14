package com.alibou.alibou.Controller;

import com.alibou.alibou.Core.IServices.IStudentService;
import com.alibou.alibou.Core.IServices.IUserService;
import com.alibou.alibou.DTO.User.KullaniciDTO;
import com.alibou.alibou.DTO.User.UserDeactiveDTO;
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
@CrossOrigin(origins = "http://localhost:3000")
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
    @GetMapping(path = "/getAllActiveUsers", produces = "application/json;charset=UTF-8")
    public ResponseEntity<List<KullaniciDTO>> getAllActiveUsers() {
        List<KullaniciDTO> activeUsers = userService.findAllActiveUsers();
        return ResponseEntity.ok(activeUsers);
    }

    @DeleteMapping("/deactivate")
    public ResponseEntity<?> deactivateUser(@RequestBody UserDeactiveDTO request) {
        try {
            userService.deactivateUserById(request.getUser_id());
            return ResponseEntity.ok("Kullanıcı pasif hale getirildi.");
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kullanıcı bulunamadı.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Bir hata oluştu.");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody KullaniciDTO updatedUserDetails) {
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
