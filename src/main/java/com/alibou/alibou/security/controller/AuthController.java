package com.alibou.alibou.security.controller;

import com.alibou.alibou.DTO.Auth.*;
import com.alibou.alibou.Model.User;
import com.alibou.alibou.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup-student")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest){
        authenticationService.signup(signUpRequest);
        return ResponseEntity.ok("Kayıt başarılı");
    }

    @PostMapping("/signup-teacher")
    public ResponseEntity<?> signupTeacher(@RequestBody SignUpTeacherRequest signUpTeacherRequest){
        authenticationService.signupTeacher(signUpTeacherRequest);
        return ResponseEntity.ok("Ekleme başarılı");
    }

    @PostMapping("/signup-parent")
    public ResponseEntity<User> signupParent(@RequestBody SignUpParentRequest signUpParentRequest){
        return ResponseEntity.ok(authenticationService.signupParent(signUpParentRequest));
    }

    @PostMapping("/signup-admin")
    public ResponseEntity<?> signupAdmin(@RequestBody SignUpAdminRequest request){
        authenticationService.signupAdmin(request);
        return ResponseEntity.ok("Admin Kayıtı Başarılı");
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthResponse> signin(@RequestBody SigninRequest signinRequest){
        return ResponseEntity.ok(authenticationService.signin(signinRequest));
    }

    @PostMapping("/webSignin")
    public ResponseEntity<?> webSignin(@RequestBody SigninRequest signinRequest){
        return ResponseEntity.ok(authenticationService.webSignin(signinRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }

    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(@RequestBody ForgetMyPasswordDTO forgetMyPasswordDTO) {
        try {
            authenticationService.forgetMyPassword(forgetMyPasswordDTO);
            return ResponseEntity.ok("Şifre başarıyla değiştirildi.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Bir hata oluştu. Lütfen tekrar deneyin.");
        }
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        try {
            authenticationService.changePassword(changePasswordDTO);
            return ResponseEntity.ok("Şifre başarıyla değiştirildi.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Bir hata oluştu. Lütfen tekrar deneyin.");
        }
    }

}
