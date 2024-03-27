package com.alibou.alibou.Controller;

import com.alibou.alibou.DTO.QR.QRCodeDTO;
import com.alibou.alibou.Service.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/qrSettings")
public class QRCodeController {
    @Autowired
    private QRCodeService qrCodeService;

    @PostMapping(value = "/generateQRCode", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQRCode(@RequestBody QRCodeDTO request) {
        try {
            byte[] qrCode = qrCodeService.generateQRCodeImage(request.getData(), request.getWidth(), request.getHeight());
            return ResponseEntity.status(HttpStatus.OK).body(qrCode);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
