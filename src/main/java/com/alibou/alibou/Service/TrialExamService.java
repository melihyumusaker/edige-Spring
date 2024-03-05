package com.alibou.alibou.Service;
import com.alibou.alibou.Core.IServices.ITrialExamService;
import com.alibou.alibou.DTO.TrialExam.DeleteTrialExamDTO;
import com.alibou.alibou.DTO.TrialExam.GetStudentTrialExamsDTO;
import com.alibou.alibou.DTO.TrialExam.SetTrialExamDTO;
import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Model.TrialExam;
import com.alibou.alibou.Repository.StudentRepository;
import com.alibou.alibou.Repository.TrialExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrialExamService implements ITrialExamService {
    private final TrialExamRepository trialExamRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public TrialExamService(TrialExamRepository trialExamRepository,StudentRepository studentRepository){
        this.trialExamRepository = trialExamRepository;
        this.studentRepository = studentRepository;
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

        float   turkceNet = turkceTrue - (turkceFalse / 4);
        float   matNet = matTrue - (matFalse / 4);
        float   sosyalNet = sosyalTrue - (sosyalFalse / 4);
        float   fenNet = fenTrue - (fenFalse / 4);
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
            trialExam.setFen_net(sosyalNet);
            trialExam.setSosyal_net(fenNet);
            trialExam.setNet(net);

            trialExamRepository.save(trialExam);

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

    public List<GetStudentTrialExamsDTO> getAllTrialExamsByStudentId(int studentId) {


        List<TrialExam> studentTrialExams = trialExamRepository.findAllByStudentId(studentId);

        // DTO'ya dönüşüm işlemi
        return studentTrialExams.stream()
                .map(this::mapTrialExamToDTO)
                .collect(Collectors.toList());
    }

    private GetStudentTrialExamsDTO mapTrialExamToDTO(TrialExam trialExam) {
        GetStudentTrialExamsDTO dto = new GetStudentTrialExamsDTO();
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

        return dto;
    }


}
