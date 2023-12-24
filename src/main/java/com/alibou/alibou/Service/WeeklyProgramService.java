package com.alibou.alibou.Service;

import com.alibou.alibou.Core.IServices.IWeeklyProgramService;
import com.alibou.alibou.DTO.WeeklyProgram.CreateWeeklyProgramDTO;
import com.alibou.alibou.DTO.WeeklyProgram.UpdateWeeklyProgramDTO;
import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Model.WeeklyProgram;
import com.alibou.alibou.Repository.StudentRepository;
import com.alibou.alibou.Repository.WeeklyProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WeeklyProgramService implements IWeeklyProgramService {
    private final WeeklyProgramRepository weeklyProgramRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public WeeklyProgramService(WeeklyProgramRepository weeklyProgramRepository,StudentRepository studentRepository){
        this.weeklyProgramRepository = weeklyProgramRepository;
        this.studentRepository = studentRepository;
    }

    public List<WeeklyProgram> getAllWeeklyPrograms(){
        return weeklyProgramRepository.findAll();
    }

    public boolean createWeeklyProgram(CreateWeeklyProgramDTO request){
        int studentId = request.getStudent_id();
        String lessonName = request.getLesson_name();
        LocalDate day = request.getDay();
        String lessonStartHour = request.getLesson_start_hour();
        String lessonEndHour = request.getLesson_end_hour();

        Optional<Student> optionalStudent = studentRepository.findById(studentId);

        if (optionalStudent.isPresent()) {
            WeeklyProgram newWeeklyProgram = WeeklyProgram.builder()
                    .student_id(optionalStudent.get())
                    .lesson_name(lessonName)
                    .day(day)
                    .lesson_start_hour(lessonStartHour)
                    .lesson_end_hour(lessonEndHour)
                    .build();

            weeklyProgramRepository.save(newWeeklyProgram);
            return true; // Başarılı ekleme
        } else {
            return false; // Öğrenci bulunamadı
        }
    }

    public boolean updateWeeklyProgram(UpdateWeeklyProgramDTO request){
        int weeklyProgramId = request.getWeekly_program_id();
        String lessonName = request.getLesson_name();
        LocalDate day = request.getDay();
        String lessonStartHour = request.getLesson_start_hour();
        String lessonEndHour = request.getLesson_end_hour();

        Optional<WeeklyProgram> optionalWeeklyProgram = weeklyProgramRepository.findById(weeklyProgramId);

        if (optionalWeeklyProgram.isPresent()) {
            WeeklyProgram existingWeeklyProgram = optionalWeeklyProgram.get();

            // Güncelleme istenen alanları kontrol et ve güncelle
            if (lessonName != null) {
                existingWeeklyProgram.setLesson_name(lessonName);
            }
            if (day != null) {
                existingWeeklyProgram.setDay(day);
            }
            if (lessonStartHour != null) {
                existingWeeklyProgram.setLesson_start_hour(lessonStartHour);
            }
            if (lessonEndHour != null) {
                existingWeeklyProgram.setLesson_end_hour(lessonEndHour);
            }

            weeklyProgramRepository.save(existingWeeklyProgram);
            return true; // Başarılı güncelleme
        } else {
            return false; // Weekly program bulunamadı
        }
    }
}
