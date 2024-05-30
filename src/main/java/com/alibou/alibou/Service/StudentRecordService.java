package com.alibou.alibou.Service;

import com.alibou.alibou.Core.IServices.IStudentRecordService;
import com.alibou.alibou.DTO.QR.GetStudentRecordsDTO;
import com.alibou.alibou.DTO.QR.GetStudentRecordsResponseDTO;
import com.alibou.alibou.DTO.QR.SaveStudentRecordsDTO;
import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Model.StudentRecord;
import com.alibou.alibou.Repository.RelationRepository;
import com.alibou.alibou.Repository.StudentRecordRepository;
import com.alibou.alibou.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentRecordService implements IStudentRecordService {

    private final StudentRecordRepository studentRecordRepository;
    private final StudentRepository studentRepository;
    @Autowired
    public StudentRecordService(StudentRecordRepository studentRecordRepository,StudentRepository studentRepository){
        this.studentRecordRepository = studentRecordRepository;
        this.studentRepository  = studentRepository;
    }

    @Override
    public void saveStudentRecords(SaveStudentRecordsDTO request) {
        Optional<Student> optionalStudent = studentRepository.findByUserId(request.getUser_id());

        if(optionalStudent.isPresent()) {
            LocalDate today = LocalDate.now();
            LocalTime now = LocalTime.now();


            Optional<StudentRecord> todayRecordOptional = studentRecordRepository.findTodayRecordByStudentId(request.getUser_id(), today);

            if (now.isAfter(LocalTime.MIDNIGHT) && now.isBefore(LocalTime.NOON)) {

                if (!todayRecordOptional.isPresent()) {
                    StudentRecord studentRecord = createStudentRecord(optionalStudent.get(), "Entered");
                    studentRecordRepository.save(studentRecord);
                }
            } else {
                todayRecordOptional.ifPresentOrElse(record -> {
                    LocalDateTime localDateTimeNow = LocalDateTime.now();
                    Date nowDate = Date.from(localDateTimeNow.atZone(ZoneId.systemDefault()).toInstant());
                    record.setStatus("Exited");
                    record.setExit_time(nowDate);
                    studentRecordRepository.save(record);
                }, () -> {
                    System.out.println("No entry record found for today.");
                });
            }
        }
    }

    private StudentRecord createStudentRecord(Student student, String status) {
        LocalDateTime now = LocalDateTime.now();
        Date nowDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

        return StudentRecord.builder()
                .student_id(student)
                .date(nowDate)
                .entry_time(nowDate)
                .status(status)
                .build();
    }

    @Override
    public GetStudentRecordsResponseDTO getStudentRecords(GetStudentRecordsDTO request) {

        List<StudentRecord> records = studentRecordRepository.findAllRecordsByStudentId(request.getStudent_id());
        int countRecordsWithNullExitTime = studentRecordRepository.countRecordsWithNullExitTime(request.getStudent_id());

        List<GetStudentRecordsResponseDTO.SpecialStudentRecordDTO> specialStudentRecordDTOList = records.stream()
                .map(record -> GetStudentRecordsResponseDTO.SpecialStudentRecordDTO.builder()
                        .date(record.getDate())
                        .entry_time(record.getEntry_time())
                        .exit_time(record.getExit_time())
                        .status(record.getStatus())
                        .build())
                .collect(Collectors.toList());

        return GetStudentRecordsResponseDTO.builder()
                .toplamDevamsizlik(countRecordsWithNullExitTime)
                .studentRecord(specialStudentRecordDTOList)
                .build();
    }





}
