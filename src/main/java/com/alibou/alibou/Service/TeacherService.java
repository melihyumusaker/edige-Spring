package com.alibou.alibou.Service;

import com.alibou.alibou.Core.IServices.ITeacherService;
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

@Service
public class TeacherService implements ITeacherService {
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository , StudentRepository studentRepository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public List<Student> getStudentsByTeacherId(int teacherId) {
        return studentRepository.findStudentsByTeacherId(teacherId);
    }

    @Override
    public int getTeacherIdByUserId(int userId) {
        Teacher teacher = teacherRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Öğretmen bulunamadı: " + userId));

        return teacher.getTeacher_id();
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
