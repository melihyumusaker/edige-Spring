package com.alibou.alibou.Controller;

import com.alibou.alibou.DTO.QR.GetStudentRecordsDTO;
import com.alibou.alibou.DTO.QR.GetStudentRecordsResponseDTO;
import com.alibou.alibou.DTO.QR.QRCodeDTO;
import com.alibou.alibou.DTO.QR.SaveStudentRecordsDTO;
import com.alibou.alibou.DTO.Teacher.GetTeacherIdByUserIdDTO;
import com.alibou.alibou.Model.StudentRecord;
import com.alibou.alibou.Service.QRCodeService;
import com.alibou.alibou.Service.StudentRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/qrSettings")
public class QRCodeController {

    @Autowired
    private QRCodeService qrCodeService;
    @Autowired
    private StudentRecordService studentRecordService;

    @PostMapping(value = "/generateQRCode", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQRCode(@RequestBody QRCodeDTO request) {
        try {
            byte[] qrCode = qrCodeService.generateQRCodeImage(request.getData(), request.getWidth(), request.getHeight());
            return ResponseEntity.status(HttpStatus.OK).body(qrCode);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping(value = "/saveStudentRecords")
    public synchronized  ResponseEntity<?> saveStudentRecords(@RequestBody SaveStudentRecordsDTO request) {
        try {
            studentRecordService.saveStudentRecords(request);
            return ResponseEntity.status(HttpStatus.OK).body("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @PostMapping(value = "/getStudentRecords" , produces = "application/json;charset=UTF-8" )
    public ResponseEntity<?> getStudentRecords(@RequestBody GetStudentRecordsDTO request) {
        try {
            GetStudentRecordsResponseDTO recorsds = studentRecordService.getStudentRecords(request);
            return  ResponseEntity.status(HttpStatus.OK).body(recorsds);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}
