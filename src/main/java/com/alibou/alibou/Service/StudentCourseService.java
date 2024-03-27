package com.alibou.alibou.Service;

import com.alibou.alibou.Core.IServices.IStudentCourseService;
import com.alibou.alibou.DTO.StudentCourse.AddNewStudentCourseAndCourseDTO;
import com.alibou.alibou.DTO.StudentCourse.AddNewStudentCourseDTO;
import com.alibou.alibou.DTO.StudentCourse.StudentFinishHomeworkDTO;
import com.alibou.alibou.Model.Course;
import com.alibou.alibou.Model.Relation;
import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Model.StudentCourse;
import com.alibou.alibou.Repository.CourseRepository;
import com.alibou.alibou.Repository.StudentCourseRepository;
import com.alibou.alibou.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentCourseService implements IStudentCourseService {
    private final StudentCourseRepository studentCourseRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public StudentCourseService(StudentCourseRepository studentCourseRepository,StudentRepository studentRepository,CourseRepository courseRepository){
        this.studentCourseRepository = studentCourseRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<StudentCourse> getAllStudentAndCourses(){
        return studentCourseRepository.findAll();
    }

    @Override
    public void addNewStudentCourse(AddNewStudentCourseDTO request) {
        Optional<Course> course = courseRepository.findById(request.getCourse_id());
        Optional<Student> student = studentRepository.findById(request.getStudent_id());

        if(course.isPresent() && student.isPresent()){
            StudentCourse studentCourse = StudentCourse.builder()
                    .student_id(student.get())
                    .course_id(course.get())
                    .build();

            studentCourseRepository.save(studentCourse);

        }

    }

    @Override
    public void addNewStudentCourseAndCourse(AddNewStudentCourseAndCourseDTO request) {
            Course course = Course.builder()
                    .course_name(request.getCourse_name())
                    .subcourse_name(request.getSubcourse_name())
                    .student_comment("")
                    .homework_description(request.getHomework_description())
                    .is_homework_done(0)
                    .homework_deadline(request.getHomework_deadline())
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
