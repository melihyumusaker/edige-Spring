package com.alibou.alibou.security.config;
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

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers("/api/v1/auth/**")
                        .permitAll()
                        .requestMatchers(
                                "/students/**" , "/parents/**","/api/v1/auth/signup-student"
                                ,"/meetings/**" ,  "/relations/**" ,  "/teachers/**" , "/users/**" , "/gpt/**"
                        ).permitAll()
                        .requestMatchers("/courses/studentFinishHomework").permitAll()
                        .requestMatchers("/courses/addNewCourse").hasAnyAuthority(Role.TEACHER.name())
                        .requestMatchers("/courses/deleteCourse").hasAnyAuthority(Role.TEACHER.name())
                        .requestMatchers("/courses/updateCourse").hasAnyAuthority(Role.TEACHER.name())
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
                        .requestMatchers("/trial-exams/setStudentTrialExamResult").permitAll()
                        .requestMatchers("/trial-exams/getStudentTrialExams").permitAll()
                        .requestMatchers("/trial-exams/getStudentTrialExamsByTeacher").permitAll()
                        .requestMatchers("/trial-exams/updateTrialExam").hasAnyAuthority(Role.TEACHER.name())
                        .requestMatchers("/trial-exams/deleteTrialExam").hasAnyAuthority(Role.TEACHER.name())
                        .requestMatchers("/message/createMessage").hasAnyAuthority(Role.TEACHER.name() , Role.ADMIN.name() , Role.STUDENT.name() , Role.PARENT.name())
                        .requestMatchers("/message/messageHistory").hasAnyAuthority(Role.TEACHER.name() , Role.ADMIN.name() , Role.STUDENT.name() , Role.PARENT.name())
                        .requestMatchers("/message/deleteAllMessages").hasAnyAuthority(Role.TEACHER.name() , Role.ADMIN.name() , Role.STUDENT.name() , Role.PARENT.name())
                        .requestMatchers("/message/messageList").hasAnyAuthority(Role.TEACHER.name() , Role.ADMIN.name() , Role.STUDENT.name() , Role.PARENT.name())
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
}