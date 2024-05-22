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
import com.alibou.alibou.Service.StudentService;
import com.alibou.alibou.Service.TeacherService;
import com.alibou.alibou.security.service.AuthenticationService;
import com.alibou.alibou.security.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
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
    private final StudentService studentService;
    private final TeacherService teacherService;

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
        student.setIs_enneagram_test_solved(0);

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
        teacher.setIs_enneagram_test_solved(0);
        teacher.setAbout(signUpTeacherRequest.getAbout());


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

    @Override
    public void signupAdmin(SignUpAdminRequest request) {
        User user = new User();

        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setRole(Role.ADMIN);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setBirth_date(request.getBirth_date());
        user.setPhone(request.getPhone());
        user.setCity(request.getCity());
        user.setIs_active(1);
        user.setUsername(UsernameGenerator.generateUsername(request.getName(), request.getSurname(),request.getPhone()));

        userRepository.save(user);
    }

    @Override
    public Object webSignin(SigninRequest signinRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(),
                signinRequest.getPassword()));

        var user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>() , user);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();

        jwtAuthResponse.setToken(jwt);
        jwtAuthResponse.setRefreshToken(refreshToken);

        if(user.getRole() == Role.TEACHER){
            Teacher teacher = teacherService.getTeacherByUserId(user.getUserId());
            WebTeacherResponse webTeacherResponse = new WebTeacherResponse();
            webTeacherResponse.setTeacher(teacher);
            webTeacherResponse.setToken(jwt);
            webTeacherResponse.setRefreshToken(refreshToken);
            webTeacherResponse.setRole(user.getRole());

            return webTeacherResponse;
        }
        else if(user.getRole() == Role.STUDENT){
            Student student = studentService.getStudentByUserId(user.getUserId());
            WebStudentResponse webStudentResponse = new WebStudentResponse();
            webStudentResponse.setStudent(student);
            webStudentResponse.setToken(jwt);
            webStudentResponse.setRefreshToken(refreshToken);
            webStudentResponse.setRole(user.getRole());

            return webStudentResponse;
        }
        else if(user.getRole() == Role.ADMIN){
            WebAdminResponse webAdminResponse = new WebAdminResponse();
            webAdminResponse.setUser(user);
            webAdminResponse.setToken(jwt);
            webAdminResponse.setRefreshToken(refreshToken);
            webAdminResponse.setRole(user.getRole());

            return webAdminResponse;
        }
        else return null;
    }
    @Override
    public void forgetMyPassword(ForgetMyPasswordDTO forgetMyPasswordDTO) {
        String newPassword = forgetMyPasswordDTO.getNewPassword();
        String oldPassword = forgetMyPasswordDTO.getOldPassword();

        User user = userRepository.findByEmail(forgetMyPasswordDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Kullanıcı bulunamadı!"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) throw new IllegalArgumentException("Eski şifre yanlış!");
        if(Objects.equals(oldPassword, newPassword)) throw new IllegalArgumentException("Eski ve Yeni Şifre Aynı Olamaz!");
        if (newPassword.length() < 8) throw new IllegalArgumentException("Yeni şifre en az 8 karakter olmalıdır!");

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void changePassword(ChangePasswordDTO changePasswordDTO) {
        User user = userRepository.findById(changePasswordDTO.getUser_id())
                .orElseThrow(() -> new IllegalArgumentException("Kullanıcı bulunamadı!"));

        String newPassword = changePasswordDTO.getNewPassword();

        if (newPassword.length() < 8) throw new IllegalArgumentException("Yeni şifre en az 8 karakter olmalıdır!");

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
