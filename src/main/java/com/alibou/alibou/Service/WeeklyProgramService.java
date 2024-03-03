package com.alibou.alibou.Service;

import com.alibou.alibou.Core.IServices.IWeeklyProgramService;
import com.alibou.alibou.DTO.WeeklyProgram.*;
import com.alibou.alibou.Model.Parent;
import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Model.WeeklyProgram;
import com.alibou.alibou.Repository.ParentRepository;
import com.alibou.alibou.Repository.StudentRepository;
import com.alibou.alibou.Repository.WeeklyProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WeeklyProgramService implements IWeeklyProgramService {
    private final WeeklyProgramRepository weeklyProgramRepository;
    private final StudentRepository studentRepository;
    private final ParentRepository parentRepository;

    @Autowired
    public WeeklyProgramService(WeeklyProgramRepository weeklyProgramRepository,StudentRepository studentRepository,ParentRepository parentRepository){
        this.weeklyProgramRepository = weeklyProgramRepository;
        this.studentRepository = studentRepository;
        this.parentRepository = parentRepository;
    }

    public List<WeeklyProgram> getAllWeeklyPrograms(){
        return weeklyProgramRepository.findAll();
    }

    public boolean createWeeklyProgram(CreateWeeklyProgramDTO request){
        int studentId = request.getStudent_id();
        String lessonName = request.getLesson_name();
        String day = request.getDay();
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
        String day = request.getDay();
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

    public List<WeeklyProgram> getWeeklyProgramByStudentId(GetWeeklyProgramByStudentIdWithRequestBodyDTO request) {
        List<WeeklyProgram> weeklyPrograms = weeklyProgramRepository.findWeeklyProgramsByStudentId(request.getStudent_id());
        List<WeeklyProgram> detailsDTOList = new ArrayList<>();

        for (WeeklyProgram weeklyProgram : weeklyPrograms) {
            WeeklyProgramDetailsDTO detailsDTO = new WeeklyProgramDetailsDTO();
            detailsDTO.setWeekly_program_id(weeklyProgram.getWeekly_program_id());
            detailsDTO.setLessonName(weeklyProgram.getLesson_name());
            detailsDTO.setDay(weeklyProgram.getDay());
            detailsDTO.setLessonStartHour(weeklyProgram.getLesson_start_hour());
            detailsDTO.setLessonEndHour(weeklyProgram.getLesson_end_hour());

            WeeklyProgram convertedProgram = convertToWeeklyProgram(detailsDTO);
            detailsDTOList.add(convertedProgram);
        }

        return detailsDTOList;
    }

    private WeeklyProgram convertToWeeklyProgram(WeeklyProgramDetailsDTO detailsDTO) {
        WeeklyProgram weeklyProgram = new WeeklyProgram();
        weeklyProgram.setWeekly_program_id(detailsDTO.getWeekly_program_id());
        weeklyProgram.setLesson_name(detailsDTO.getLessonName());
        weeklyProgram.setDay(detailsDTO.getDay());
        weeklyProgram.setLesson_start_hour(detailsDTO.getLessonStartHour());
        weeklyProgram.setLesson_end_hour(detailsDTO.getLessonEndHour());

        return weeklyProgram;
    }

    public Optional<List<WeeklyProgramDetailsDTO>> findStudentWeeklyProgramByParentId(GetParentChildWeeklyProgramDTO request) {
        Optional<Parent> parent = parentRepository.findById(request.getParent_id());
        if (parent.isPresent()) {
            int student_id = parent.get().getStudent().getStudent_id();
            List<WeeklyProgram> weeklyPrograms = weeklyProgramRepository.findWeeklyProgramsByStudentId(student_id);

            // Burada WeeklyProgram listesini WeeklyProgramDetailsDTO listesine dönüştür
            List<WeeklyProgramDetailsDTO> detailsDTOList = weeklyPrograms.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return Optional.of(detailsDTOList);
        } else {
            return Optional.empty();
        }
    }

    private WeeklyProgramDetailsDTO convertToDTO(WeeklyProgram weeklyProgram) {
        WeeklyProgramDetailsDTO detailsDTO = new WeeklyProgramDetailsDTO();
        detailsDTO.setLessonName(weeklyProgram.getLesson_name());
        detailsDTO.setDay(weeklyProgram.getDay());
        detailsDTO.setLessonStartHour(weeklyProgram.getLesson_start_hour());
        detailsDTO.setLessonEndHour(weeklyProgram.getLesson_end_hour());
        return detailsDTO;
    }


}
