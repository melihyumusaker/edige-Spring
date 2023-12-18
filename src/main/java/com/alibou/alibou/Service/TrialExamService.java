package com.alibou.alibou.Service;
import com.alibou.alibou.Core.IServices.ITrialExamService;
import com.alibou.alibou.DTO.TrialExam.SetTrialExamDTO;
import com.alibou.alibou.Model.TrialExam;
import com.alibou.alibou.Repository.TrialExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrialExamService implements ITrialExamService {
    private final TrialExamRepository trialExamRepository;

    @Autowired
    public TrialExamService(TrialExamRepository trialExamRepository){
        this.trialExamRepository = trialExamRepository;
    }

    public List<TrialExam> getAllTrialExams(){
        return trialExamRepository.findAll();
    }

    @Override
    public TrialExam saveStudentTrialExamResult(SetTrialExamDTO request) {
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

        trialExam.setStudent_id(request.getStudent());
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

        return trialExam;
    }

}
