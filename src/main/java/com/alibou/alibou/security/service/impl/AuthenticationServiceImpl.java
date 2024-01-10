package com.alibou.alibou.security.service.impl;

import com.alibou.alibou.Core.Roles.Role;
import com.alibou.alibou.Core.Utils.UsernameGenerator;
import com.alibou.alibou.DTO.Auth.*;
import com.alibou.alibou.Model.Parent;
import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Model.Teacher;
import com.alibou.alibou.Model.User;
import com.alibou.alibou.Repository.ParentRepository;
import com.alibou.alibou.Repository.StudentRepository;
import com.alibou.alibou.Repository.TeacherRepository;
import com.alibou.alibou.Repository.UserRepository;
import com.alibou.alibou.security.service.AuthenticationService;
import com.alibou.alibou.security.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final ParentRepository parentRepository;
    @Override
    public User signup(SignUpRequest signUpRequest) {
        User user = new User();
        Student student = new Student();

        user.setEmail(signUpRequest.getEmail());
        user.setName(signUpRequest.getName());
        user.setSurname(signUpRequest.getSurname());
        user.setRole(Role.STUDENT);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setBirth_date(signUpRequest.getBirth_date());
        user.setPhone(signUpRequest.getPhone());
        user.setCity(signUpRequest.getCity());
        user.setUsername(UsernameGenerator.generateUsername(signUpRequest.getName(), signUpRequest.getSurname(),signUpRequest.getPhone()));
        user.setIs_active(1);
        student.setSection(signUpRequest.getSection());
        student.setSchool(signUpRequest.getSchool());

        userRepository.save(user);

        Optional<User> newUserOptional = userRepository.findByEmail(user.getEmail());
        if (newUserOptional.isPresent()) {
            User newUser = newUserOptional.get();
            student.setUser(newUser);
        } else {
            throw new RuntimeException("Kullanıcı bulunamadı!");
        }

        studentRepository.save(student);
        return user;
    }
    @Override
    public User signupTeacher(SignUpTeacherRequest signUpTeacherRequest) {
        User user = new User();
        Teacher teacher = new Teacher();

        user.setEmail(signUpTeacherRequest.getEmail());
        user.setName(signUpTeacherRequest.getName());
        user.setSurname(signUpTeacherRequest.getSurname());
        user.setRole(Role.TEACHER);
        user.setPassword(passwordEncoder.encode(signUpTeacherRequest.getPassword()));
        user.setBirth_date(signUpTeacherRequest.getBirth_date());
        user.setPhone(signUpTeacherRequest.getPhone());
        user.setCity(signUpTeacherRequest.getCity());
        user.setIs_active(1);
        user.setUsername(UsernameGenerator.generateUsername(signUpTeacherRequest.getName(), signUpTeacherRequest.getSurname(),signUpTeacherRequest.getPhone()));
        teacher.setExpertise(signUpTeacherRequest.getExpertise());

        userRepository.save(user);

        Optional<User> newUserOptional = userRepository.findByEmail(user.getEmail());
        if (newUserOptional.isPresent()) {
            User newUser = newUserOptional.get();
            teacher.setUser(newUser);
        } else {
            throw new RuntimeException("Kullanıcı bulunamadı!");
        }

        teacherRepository.save(teacher);
        return user;
    }

    @Override
    public User signupParent(SignUpParentRequest signUpParentRequest) {
        User user = new User();
        Parent parent = new Parent();

        user.setEmail(signUpParentRequest.getEmail());
        user.setName(signUpParentRequest.getName());
        user.setSurname(signUpParentRequest.getSurname());
        user.setRole(Role.PARENT);
        user.setPassword(passwordEncoder.encode(signUpParentRequest.getPassword()));
        user.setBirth_date(signUpParentRequest.getBirth_date());
        user.setPhone(signUpParentRequest.getPhone());
        user.setCity(signUpParentRequest.getCity());
        user.setIs_active(1);
        user.setUsername(UsernameGenerator.generateUsername(signUpParentRequest.getName(), signUpParentRequest.getSurname(),signUpParentRequest.getPhone()));

        userRepository.save(user);

        parent.setStudent(signUpParentRequest.getStudent());

        Optional<User> newUserOptional = userRepository.findById(user.getUser_id());

        if (newUserOptional.isPresent()) {
            User newUser = newUserOptional.get();
            parent.setUser(newUser);
        } else {
            throw new RuntimeException("Kullanıcı bulunamadı!");
        }

        parentRepository.save(parent);
        return user;
    }
    public JwtAuthResponse signin(SigninRequest signinRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(),
                signinRequest.getPassword()));

        var user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>() , user);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();

        jwtAuthResponse.setToken(jwt);
        jwtAuthResponse.setRefreshToken(refreshToken);
        return jwtAuthResponse;
    }

    public JwtAuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        if(jwtService.isTokenValid(refreshTokenRequest.getToken() , user)){
            var jwt = jwtService.generateToken(user);
            JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();

            jwtAuthResponse.setToken(jwt);
            jwtAuthResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthResponse;
        }
        return null;
    }
}
