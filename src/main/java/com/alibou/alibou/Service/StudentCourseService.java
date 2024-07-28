package com.alibou.alibou.Service;

import com.alibou.alibou.Core.IServices.IStudentCourseService;
import com.alibou.alibou.DTO.StudentCourse.AddNewStudentCourseAndCourseDTO;
import com.alibou.alibou.DTO.StudentCourse.AddNewStudentCourseDTO;
import com.alibou.alibou.DTO.StudentCourse.StudentFinishHomeworkDTO;
import com.alibou.alibou.Model.*;
import com.alibou.alibou.Repository.CourseRepository;
import com.alibou.alibou.Repository.NotificationStudentRepository;
import com.alibou.alibou.Repository.StudentCourseRepository;
import com.alibou.alibou.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentCourseService implements IStudentCourseService {
    private final StudentCourseRepository studentCourseRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final NotificationStudentRepository notificationStudentRepository;

    @Autowired
    public StudentCourseService(StudentCourseRepository studentCourseRepository,StudentRepository studentRepository,CourseRepository courseRepository,NotificationStudentRepository notificationStudentRepository){
        this.studentCourseRepository = studentCourseRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.notificationStudentRepository = notificationStudentRepository;
    }

    public List<StudentCourse> getAllStudentAndCourses(){
        return studentCourseRepository.findAll();
    }

    @Override
    public void addNewStudentCourse(AddNewStudentCourseDTO request) {

        LocalTime now = LocalTime.now();

        Optional<Course> course = courseRepository.findById(request.getCourse_id());
        Optional<Student> student = studentRepository.findById(request.getStudent_id());

        if(course.isPresent() && student.isPresent()){
            StudentCourse studentCourse = StudentCourse.builder()
                    .student_id(student.get())
                    .course_id(course.get())
                    .build();
            studentCourseRepository.save(studentCourse);

            NotificationStudent notificationStudent = NotificationStudent.builder().
                    student_id(student.get())
                    .message("Yeni Ödev Eklendi")
                    .create_at(java.sql.Date.valueOf(LocalDate.now()))
                    .is_shown(false).build();
            notificationStudentRepository.save(notificationStudent);
        }
    }
    public int countUnshownCoursesByStudentId(int studentId) {
        return studentCourseRepository.countUnshownCoursesByStudentId(studentId);
    }
    @Override
    public void addNewStudentCourseAndCourse(AddNewStudentCourseAndCourseDTO request) {
        LocalTime now = LocalTime.now();
        LocalDate today = LocalDate.now();

            Course course = Course.builder()
                    .course_name(request.getCourse_name())
                    .subcourse_name(request.getSubcourse_name())
                    .student_comment("")
                    .homework_description(request.getHomework_description())
                    .is_homework_done(0)
                    .homework_deadline(request.getHomework_deadline())
                    .is_shown(0)
                    .build();

            courseRepository.save(course);

            int newCourseId = course.getCourse_id();

        Optional<Course> newCourse = courseRepository.findById(newCourseId);
        Optional<Student> student = studentRepository.findById(request.getStudent_id());

        if(newCourse.isPresent() && student.isPresent()){
            StudentCourse studentCourse = StudentCourse.builder()
                    .student_id(student.get())
                    .course_id(newCourse.get())
                    .build();

            studentCourseRepository.save(studentCourse);

            NotificationStudent notificationStudent = NotificationStudent.builder().
                    student_id(student.get())
                    .message("Yeni Ödev Eklendi: " + request.getCourse_name() + " " + request.getSubcourse_name())
                    .create_at(Date.from(now.atDate(today).atZone(ZoneId.systemDefault()).toInstant()))
                    .is_shown(false).build();

            System.out.println("Notif kaydedildi");

            notificationStudentRepository.save(notificationStudent);
        }
    }

    public List<Course> getAllCoursesByStudentId(int studentId) {
        try{
            return studentCourseRepository.findAllByStudentId(studentId)
                    .stream()
                    .map(StudentCourse::getCourse_id)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new RuntimeException("Error while fetching not done courses for student with ID: " + studentId, ex);
        }
    }

    public List<Course> getDoneCoursesByStudentIdAndIsHomeworkDone(int studentId) {
       try{
           return studentCourseRepository.findAllByStudentIdAndIsHomeworkDone(studentId)
                   .stream()
                   .map(StudentCourse::getCourse_id)
                   .collect(Collectors.toList());
       } catch (Exception ex) {
           throw new RuntimeException("Error while fetching done courses for student with ID: " + studentId, ex);
       }
    }

    public List<Course> getNotDoneCoursesByStudentIdAndIsHomeworkDone(int studentId) {
        try{
            return studentCourseRepository.findAllByStudentIdAndIsHomeworkNotDone(studentId)
                    .stream()
                    .map(StudentCourse::getCourse_id)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new RuntimeException("Error while fetching not done courses for student with ID: " + studentId, ex);
        }
    }



}
