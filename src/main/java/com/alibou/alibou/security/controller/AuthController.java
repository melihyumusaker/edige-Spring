package com.alibou.alibou.security.controller;

import com.alibou.alibou.DTO.Auth.*;
import com.alibou.alibou.Model.User;
import com.alibou.alibou.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }

}
