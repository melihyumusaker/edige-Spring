package com.alibou.alibou.Service;

import com.alibou.alibou.Core.IServices.IStudentCourseService;
import com.alibou.alibou.Model.Course;
import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Model.StudentCourse;
import com.alibou.alibou.Repository.StudentCourseRepository;
import com.alibou.alibou.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentCourseService implements IStudentCourseService {
    private final StudentCourseRepository studentCourseRepository;

    @Autowired
    public StudentCourseService(StudentCourseRepository studentCourseRepository){
        this.studentCourseRepository = studentCourseRepository;
    }

    public List<StudentCourse> getAllStudentAndCourses(){
        return studentCourseRepository.findAll();
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
