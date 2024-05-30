package com.alibou.alibou.Service;

import com.alibou.alibou.Core.IServices.ITeacherService;
import com.alibou.alibou.DTO.Student.GetAllStudentResponseDTO;
import com.alibou.alibou.DTO.Teacher.GetAllTeacherDTO;
import com.alibou.alibou.DTO.Teacher.UpdateTeacherEnneagramTypeAndAboutDTO;
import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Model.Teacher;
import com.alibou.alibou.Model.WeeklyProgram;
import com.alibou.alibou.Repository.StudentRepository;
import com.alibou.alibou.Repository.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherService implements ITeacherService {
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository , StudentRepository studentRepository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    public List<GetAllTeacherDTO> getAllTeachers() {
       List<Teacher> teachers = teacherRepository.findAll();
        List<GetAllTeacherDTO> response = teachers.stream().map(teacher -> GetAllTeacherDTO.builder()
                        .name(teacher.getUser().getName())
                        .surname(teacher.getUser().getSurname())
                        .birth_date(teacher.getUser().getBirth_date())
                        .email(teacher.getUser().getEmail())
                        .phone(teacher.getUser().getPhone())
                        .city(teacher.getUser().getCity())
                        .enneagram_result(teacher.getEnneagram_result())
                        .about(teacher.getAbout())
                        .expertise(teacher.getExpertise())
                        .build())
                .collect(Collectors.toList());

        return response;
    }

    @Override
    public List<Student> getStudentsByTeacherId(int teacherId) {
        return studentRepository.findStudentsByTeacherId(teacherId);
    }

    @Override
    public List<GetAllStudentResponseDTO> getStudentsByTeacherId1(int teacherId) {
        List<Student> students = studentRepository.findStudentsByTeacherId(teacherId);
        List<GetAllStudentResponseDTO> response = students.stream().map(student -> GetAllStudentResponseDTO.builder()
                        .name(student.getUser().getName())
                        .surname(student.getUser().getSurname())
                        .birth_date(student.getUser().getBirth_date())
                        .email(student.getUser().getEmail())
                        .phone(student.getUser().getPhone())
                        .city(student.getUser().getCity())
                        .enneagram_result(student.getEnneagram_result())
                        .section(student.getSection())
                        .school(student.getSchool())
                        .coachName(student.getTeacher().getUser().getName() + " " + student.getTeacher().getUser().getSurname()).build())
                .collect(Collectors.toList());
        return response;
    }


    @Override
    public int getTeacherIdByUserId(int userId) {
        Teacher teacher = teacherRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Öğretmen bulunamadı: " + userId));

        return teacher.getTeacher_id();
    }

    @Override
    public Teacher getTeacherByUserId(int userId) {
        Teacher teacher = teacherRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Öğretmen bulunamadı: " + userId));

        if(teacher != null){
            return teacher;
        }else return null;
    }

    @Override
    public List<Teacher> getTeachersByStudentType(String studentType) {
        List<Teacher> teachers = teacherRepository.findTeachersByStudentType(studentType);
        Collections.shuffle(teachers);
        return teachers;
    }

    @Override
    public Teacher getTeacherDetails(int teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElse(null);
        if (teacher == null) {
            return null;
        }else{
            return teacher;
        }
    }

    @Override
    public boolean updateTeacherAboutAndEnneagramTestSolved(UpdateTeacherEnneagramTypeAndAboutDTO request, int teacherId) {
        String about = request.getAbout();
        String enneagram_result = request.getEnneagram_result();
        int isEnneagramTestSolved = request.getIs_enneagram_test_solved();

        Optional<Teacher> optionalTeacher = teacherRepository.findById(teacherId);

        if (optionalTeacher.isPresent()) {
            Teacher existingTeacher = optionalTeacher.get();

            // Güncelleme istenen alanları kontrol et ve güncelle
            if (about != null) {
                existingTeacher.setAbout(about);
            }
            if (enneagram_result != null) {
                existingTeacher.setEnneagram_result(enneagram_result);
            }
            if (isEnneagramTestSolved != 0) {
                existingTeacher.setIs_enneagram_test_solved(isEnneagramTestSolved);
            }

            teacherRepository.save(existingTeacher);
            return true; // Başarılı güncelleme
        } else {
            return false; // Weekly program bulunamadı
        }

    }
}
