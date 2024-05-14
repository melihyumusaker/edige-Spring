package com.alibou.alibou.security.config;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import com.alibou.alibou.Core.Roles.Role;
import com.alibou.alibou.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableCaching
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("http://localhost:50785");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request.requestMatchers(
                                "/students/**" , "/parents/**"
                                 ,  "/relations/**" ,  "/teachers/**" , "/users/**" , "/gpt/**"
                                ).permitAll()
                        .requestMatchers("/api/v1/auth/signup-student").permitAll()
                        .requestMatchers("/api/v1/auth/signup-teacher").permitAll()
                        .requestMatchers("/api/v1/auth/signup-parent").permitAll()
                        .requestMatchers("/api/v1/auth/signup-admin").permitAll()
                        .requestMatchers("/api/v1/auth/signin").permitAll()
                        .requestMatchers("/api/v1/auth/webSignin").permitAll()
                        .requestMatchers("/api/v1/auth/refresh").permitAll()
                        .requestMatchers("/api/v1/auth/forget-password").hasAnyAuthority(Role.TEACHER.name() , Role.ADMIN.name() , Role.STUDENT.name() , Role.PARENT.name())
                        .requestMatchers("/api/v1/auth/change-password").hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers("/courses/studentFinishHomework").permitAll()
                        .requestMatchers("/courses/addNewCourse").hasAnyAuthority(Role.TEACHER.name())
                        .requestMatchers("/courses/deleteCourse").hasAnyAuthority(Role.TEACHER.name())
                        .requestMatchers("/courses/updateCourse").hasAnyAuthority(Role.TEACHER.name() , Role.STUDENT.name())
                        .requestMatchers("/students-courses/addNewStudentCourse").hasAnyAuthority(Role.TEACHER.name())
                        .requestMatchers("/weekly-programs/deleteWeeklyProgram").hasAnyAuthority(Role.TEACHER.name())
                        .requestMatchers("/weekly-programs/all").permitAll()
                        .requestMatchers("/weekly-programs/createWeeklyProgram").permitAll()
                        .requestMatchers("/weekly-programs/updateWeeklyProgram").permitAll()
                        .requestMatchers("/weekly-programs/getWeeklyProgramByStudentId").permitAll()
                        .requestMatchers("/weekly-programs/getParentChildWeeklyProgram").permitAll()
                        .requestMatchers("/students-courses/get-all-students-courses").permitAll()
                        .requestMatchers("/students-courses/get-students-done-courses").permitAll()
                        .requestMatchers("/students-courses/get-students-not-done-courses").permitAll()
                        .requestMatchers("/students-courses/addNewStudentCourseAndCourse").hasAnyAuthority(Role.TEACHER.name())
                        .requestMatchers("/trial-exams/setStudentTrialExamResult").permitAll()
                        .requestMatchers("/trial-exams/getStudentTrialExams").permitAll()
                        .requestMatchers("/trial-exams/getStudentTrialExamsByTeacher").permitAll()
                        .requestMatchers("/trial-exams/updateTrialExam").hasAnyAuthority(Role.TEACHER.name())
                                .requestMatchers("/trial-exams/updateTrialExamIsShownValue").hasAnyAuthority(Role.STUDENT.name())
                                .requestMatchers("/trial-exams/countUnshown").hasAnyAuthority(Role.STUDENT.name())
                        .requestMatchers("/trial-exams/deleteTrialExam").hasAnyAuthority(Role.TEACHER.name())
                                .requestMatchers("/meetings/countUnshown").hasAnyAuthority(Role.STUDENT.name())
                        .requestMatchers("/message/createMessage").hasAnyAuthority(Role.TEACHER.name() , Role.ADMIN.name() , Role.STUDENT.name() , Role.PARENT.name())
                        .requestMatchers("/message/messageHistory").hasAnyAuthority(Role.TEACHER.name() , Role.ADMIN.name() , Role.STUDENT.name() , Role.PARENT.name())
                        .requestMatchers("/message/deleteAllMessages").hasAnyAuthority(Role.TEACHER.name() , Role.ADMIN.name() , Role.STUDENT.name() , Role.PARENT.name())
                        .requestMatchers("/message/messageList").hasAnyAuthority(Role.TEACHER.name() , Role.ADMIN.name() , Role.STUDENT.name() , Role.PARENT.name())
                        .requestMatchers("/qrSettings/generateQRCode").hasAnyAuthority(Role.STUDENT.name())
                        .requestMatchers("/qrSettings/saveStudentRecords").permitAll()
                        .requestMatchers("/lessons/grades").hasAnyAuthority(Role.TEACHER.name())
                        .requestMatchers("/lessons/lessonNames").hasAnyAuthority(Role.TEACHER.name())
                        .requestMatchers("/lessons/sublessonNames").hasAnyAuthority(Role.TEACHER.name())
                        .requestMatchers("/lessons/sublessonNameDetails").hasAnyAuthority(Role.TEACHER.name())
                        .requestMatchers("/lessons/getAllLessons").hasAnyAuthority(Role.TEACHER.name())
                        .requestMatchers("/students-courses/unshownCourseNumber").hasAnyAuthority(Role.STUDENT.name())
                        .requestMatchers("/meetings/updateMeeting").hasAnyAuthority(Role.TEACHER.name())
                        .requestMatchers("/meetings/deleteMeeting").hasAnyAuthority(Role.TEACHER.name())
                        .requestMatchers("/meetings/createMeeting").hasAnyAuthority(Role.TEACHER.name())
                        .requestMatchers("/meetings/getTeacherAllMeetings").hasAnyAuthority(Role.TEACHER.name())
                        .requestMatchers("/meetings/getStudentAndTeacherSpecialMeetings").hasAnyAuthority(Role.STUDENT.name() , Role.TEACHER.name())
                        .anyRequest().authenticated())
                .cors(Customizer.withDefaults())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthenticationFilter , UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService.userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean
    public CaffeineCacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("lessons");
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.DAYS)
                .maximumSize(1000));
        return cacheManager;
    }
}
