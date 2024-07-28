package com.alibou.alibou.Service;
import com.alibou.alibou.Core.IServices.ITrialExamService;
import com.alibou.alibou.DTO.TrialExam.*;
import com.alibou.alibou.Model.NotificationStudent;
import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Model.TrialExam;
import com.alibou.alibou.Repository.NotificationStudentRepository;
import com.alibou.alibou.Repository.StudentRepository;
import com.alibou.alibou.Repository.TrialExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrialExamService implements ITrialExamService {
    private final TrialExamRepository trialExamRepository;
    private final StudentRepository studentRepository;
    private final NotificationStudentRepository notificationStudentRepository;

    @Autowired
    public TrialExamService(NotificationStudentRepository notificationStudentRepository,TrialExamRepository trialExamRepository,StudentRepository studentRepository){
        this.trialExamRepository = trialExamRepository;
        this.studentRepository = studentRepository;
        this.notificationStudentRepository = notificationStudentRepository;
    }

    public List<TrialExam> getAllTrialExams(){
        return trialExamRepository.findAll();
    }

    @Override
    public boolean saveStudentTrialExamResult(SetTrialExamDTO request) {
        TrialExam trialExam = new TrialExam();

        float   turkceTrue = request.getTurkce_true();
        float   turkceFalse = request.getTurkce_false();
        float   matTrue = request.getMat_true();
        float   matFalse = request.getMat_false();
        float   sosyalTrue = request.getSosyal_true();
        float   sosyalFalse = request.getSosyal_false();
        float   fenTrue = request.getFen_true();
        float   fenFalse = request.getFen_false();

        float   turkceNet = turkceTrue - (turkceFalse / 4.0f);
        float   matNet = matTrue - (matFalse / 4.0f);
        float   sosyalNet = sosyalTrue - (sosyalFalse / 4.0f);
        float   fenNet = fenTrue - (fenFalse / 4.0f);

        System.out.println( "sosyal net : " + sosyalNet + " " + fenNet);

        float   net = turkceNet + matNet + sosyalNet + fenNet;

        Optional<Student> optionalStudent = studentRepository.findById(request.getStudent_id());

        if (optionalStudent.isPresent()){
            trialExam.setStudent_id(optionalStudent.get());
            trialExam.setExam_name(request.getExam_name());
            trialExam.setTurkce_false(turkceFalse);
            trialExam.setTurkce_true(turkceTrue);
            trialExam.setSosyal_false(sosyalFalse);
            trialExam.setSosyal_true(sosyalTrue);
            trialExam.setMat_false(matFalse);
            trialExam.setMat_true(matTrue);
            trialExam.setFen_false(fenFalse);
            trialExam.setFen_true(fenTrue);
            trialExam.setDate(request.getDate());
            trialExam.setTurkce_net(turkceNet);
            trialExam.setMat_net(matNet);
            trialExam.setFen_net(fenNet);
            trialExam.setSosyal_net(sosyalNet);
            trialExam.setNet(net);
            trialExam.setIs_shown(0);

            trialExamRepository.save(trialExam);

            NotificationStudent notificationStudent = NotificationStudent.builder().
                    student_id(optionalStudent.get())
                    .message("Deneme Sınavı Sonucun Eklendi")
                    .create_at(java.sql.Date.valueOf(LocalDate.now()))
                    .is_shown(false).build();
            notificationStudentRepository.save(notificationStudent);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void deleteTrialExam(DeleteTrialExamDTO request) {
        int trialExamId = request.getTrial_exam_id();

        Optional<TrialExam> optionalTrialExam = trialExamRepository.findById(trialExamId);

        try {
            if (optionalTrialExam.isPresent()) {
                trialExamRepository.deleteById(trialExamId);
            } else {
                throw new NoSuchElementException("Trial Exam bulunamadı.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public boolean updateTrialExam(TrialExamUpdateDTO request) {
        Optional<TrialExam> optionalTrialExam = trialExamRepository.findById(request.getTrial_exam_id());

        if (optionalTrialExam.isPresent()) {
            TrialExam trialExam = optionalTrialExam.get();

            float   turkceNet = request.getTurkce_true() - (request.getTurkce_false() / 4);
            float   matNet = request.getMat_true() - (request.getMat_false() / 4);
            float   sosyalNet = request.getSosyal_true() - (request.getSosyal_false() / 4);
            float   fenNet = request.getFen_true() - (request.getFen_false() / 4);
            float   net = turkceNet + matNet + sosyalNet + fenNet;


            trialExam.setDate(request.getDate());
            trialExam.setTurkce_true(request.getTurkce_true());
            trialExam.setTurkce_false(request.getTurkce_false());
            trialExam.setMat_true(request.getMat_true());
            trialExam.setMat_false(request.getMat_false());
            trialExam.setFen_true(request.getFen_true());
            trialExam.setFen_false(request.getFen_false());
            trialExam.setSosyal_true(request.getSosyal_true());
            trialExam.setSosyal_false(request.getSosyal_false());
            trialExam.setExam_name(request.getExam_name());
            trialExam.setTurkce_net(turkceNet);
            trialExam.setSosyal_net(sosyalNet);
            trialExam.setMat_net(matNet);
            trialExam.setFen_net(fenNet);
            trialExam.setNet(net);

            trialExamRepository.save(trialExam);

            NotificationStudent notificationStudent = NotificationStudent.builder().
                    student_id(optionalTrialExam.get().getStudent_id())
                    .message(optionalTrialExam.get().getExam_name() + " Adlı Deneme Sınavın Güncellendi")
                    .create_at(java.sql.Date.valueOf(LocalDate.now()))
                    .is_shown(false).build();
            notificationStudentRepository.save(notificationStudent);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateTrialExamIsShownValue(UpdateTrialExamIsShownValueDTO request) {
        Optional<TrialExam> optionalTrialExam = trialExamRepository.findById(request.getTrial_exam_id());

        if (optionalTrialExam.isPresent()) {
            TrialExam trialExam = optionalTrialExam.get();
            trialExam.setIs_shown(1);
            trialExamRepository.save(trialExam);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int countUnshownExamsByStudentId(GetIsShownNumberDTO request) {
        try {
            return trialExamRepository.countByStudentIdAndIsShownFalse(request.getStudent_id());
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while counting unshown exams: " + ex.getMessage());
        }
    }

    public List<GetStudentTrialExamsDTO> getAllTrialExamsByStudentId(int studentId) {
        List<TrialExam> studentTrialExams = trialExamRepository.findAllByStudentIdOrderByDateAsc(studentId);
        return studentTrialExams.stream()
                .map(this::mapTrialExamToDTO)
                .collect(Collectors.toList());
    }

    private GetStudentTrialExamsDTO mapTrialExamToDTO(TrialExam trialExam) {
        GetStudentTrialExamsDTO dto = new GetStudentTrialExamsDTO();
        dto.setTrial_exam_id(trialExam.getTrial_exam_id());
        dto.setDate(trialExam.getDate());
        dto.setTurkce_true(trialExam.getTurkce_true());
        dto.setTurkce_false(trialExam.getTurkce_false());
        dto.setMat_true(trialExam.getMat_true());
        dto.setMat_false(trialExam.getMat_false());
        dto.setFen_true(trialExam.getFen_true());
        dto.setFen_false(trialExam.getFen_false());
        dto.setSosyal_true(trialExam.getSosyal_true());
        dto.setSosyal_false(trialExam.getSosyal_false());
        dto.setExam_name(trialExam.getExam_name());
        dto.setTurkce_net(trialExam.getTurkce_net());
        dto.setFen_net(trialExam.getFen_net());
        dto.setMat_net(trialExam.getMat_net());
        dto.setSosyal_net(trialExam.getSosyal_net());
        dto.setNet(trialExam.getNet());
        dto.setIs_shown(trialExam.getIs_shown());

        return dto;
    }


}
