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
import com.alibou.alibou.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.*;
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
    public synchronized String saveStudentRecords(SaveStudentRecordsDTO request) {
        Optional<Student> optionalStudent = studentRepository.findByUserId(request.getUser_id());

        if (optionalStudent.isPresent()) {
            LocalDate today = LocalDate.now();
            LocalTime now = LocalTime.now();
            String name = optionalStudent.get().getUser().getName();
            Optional<StudentRecord> todayRecordOptional = studentRecordRepository.findTodayRecordByStudentId(request.getUser_id(), today);

            if (!todayRecordOptional.isPresent()) {
                return "Beklenmedik durum oluştu. Devamsızlık kaydı otomatik oluşturulmamış";
            } else {
                System.out.println("Bugün için kayıt bulundu.");
                StudentRecord todayRecord = todayRecordOptional.get();
                System.out.println("Entry time: " + todayRecord.getEntry_time());

                if (todayRecord.getEntry_time() != null) {
                    System.out.println(" todayRecord.getEntry_time() Entry time: " + todayRecord.getEntry_time());
                    LocalTime lastEntryTime = todayRecord.getEntry_time();
                    System.out.println("Son giriş zamanı: " + lastEntryTime);
                    System.out.println("Şu anki zaman: " + now);

                    // Zaman farkını hesapla ve logla
                    long minutesBetween = Duration.between(todayRecord.getEntry_time(), now).toMinutes();
                    System.out.println("Son giriş ile şu anki zaman arasındaki fark (dakika): " + minutesBetween);

                    // Son girişin 1 dakikadan daha önce yapılıp yapılmadığını kontrol et
                    if (minutesBetween >= 1) {
                        // Durumu "Exited" olarak güncelle ve kaydet
                        todayRecord.setStatus("Exited");
                        todayRecord.setExit_time(Date.from(now.atDate(today).atZone(ZoneId.systemDefault()).toInstant()));
                        studentRecordRepository.save(todayRecord);
                        System.out.println("Exited kaydı alındı ve güncellendi.");
                        return "Görüşürüz " + name;
                    } else {
                        System.out.println("QR kodu son girişten 1 dakika içinde tarandı. Tekrar taramayı yok say.");
                        return "QR kodunuz tarandı. " + name;
                    }
                } else {
                    todayRecord.setStatus("Entered");
                    todayRecord.setEntry_time(now);
                    studentRecordRepository.save(todayRecord);
                    return "Hoşgeldin " + name;
                }
            }
        } else {
            System.out.println("Öğrenci bulunamadı.");
            return "Öğrenci bulunamadı.";
        }
    }




    private StudentRecord createStudentRecord(Student student, String status) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        Date nowDate = Date.from(now.atDate(today).atZone(ZoneId.systemDefault()).toInstant());

        return StudentRecord.builder()
                .student_id(student)
                .date(java.sql.Date.valueOf(today))
                .entry_time(now)
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

    @Override
    public int getStudentTotalRecord(GetStudentRecordsDTO request) {

        int countRecordsWithNullExitTime = studentRecordRepository.countRecordsWithNullExitTime(request.getStudent_id());
        return countRecordsWithNullExitTime;
    }


    @Transactional
    @Scheduled(fixedRate = 86400000) // Her 24 saatte bir çalışacak
    public void saveStudentRecordsDaily() {
        LocalDate today = LocalDate.now();

        // Bugün Pazar ise işlemi iptal et
        if (today.getDayOfWeek() == DayOfWeek.SUNDAY) {
            System.out.println("Bugün Pazar, kayıt oluşturulmadı.");
            return;
        }

        Date nowDate = java.sql.Date.valueOf(today);
        List<Integer> studentIds = studentRepository.findAllStudentIds();

        studentIds.forEach(studentId -> {
            Optional<Student> optionalStudent = studentRepository.findById(studentId);
            if (optionalStudent.isPresent()) {
                Student student = optionalStudent.get();
                StudentRecord studentRecord = StudentRecord.builder()
                        .student_id(student)
                        .date(nowDate)
                        .status("Empty")
                        .build();
                studentRecordRepository.save(studentRecord);
            } else {
                System.out.println("Öğrenci bulunamadı: " + studentId);
            }
        });
    }
}
