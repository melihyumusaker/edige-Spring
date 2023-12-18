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
        // Önce null check yapın ve varsayılan değeri belirleyin
        double turkceTrue = request.getTurkce_true();
        double turkceFalse = request.getTurkce_false();
        double matTrue = request.getMat_true();
        double matFalse = request.getMat_false();
        double sosyalTrue = request.getSosyal_true();
        double sosyalFalse = request.getSosyal_false();
        double fenTrue = request.getFen_true();
        double fenFalse = request.getFen_false();

        double turkceNet = turkceTrue - (turkceFalse / 4);
        double matNet = matTrue - (matFalse / 4);
        double sosyalNet = sosyalTrue - (sosyalFalse / 4);
        double fenNet = fenTrue - (fenFalse / 4);
        double net = turkceNet + matNet + sosyalNet + fenNet;

        trialExam.setStudent_id(request.getStudent_id());
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
