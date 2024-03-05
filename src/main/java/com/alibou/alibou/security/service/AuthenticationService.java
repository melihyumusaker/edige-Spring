package com.alibou.alibou.security.service;

import com.alibou.alibou.DTO.Auth.*;
import com.alibou.alibou.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface AuthenticationService  {
    User signup(SignUpRequest signUpRequest);
    User signupTeacher(SignUpTeacherRequest signUpTeacherRequest);
    User signupParent(SignUpParentRequest signUpParentRequest);
    JwtAuthResponse signin(SigninRequest signinRequest);
    JwtAuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    void signupAdmin(SignUpAdminRequest request);
}
