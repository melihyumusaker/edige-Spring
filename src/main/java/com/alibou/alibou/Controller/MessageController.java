package com.alibou.alibou.Controller;

import com.alibou.alibou.Core.IServices.IMessageService;
import com.alibou.alibou.DTO.Message.DeleteAllMessagesDTO;
import com.alibou.alibou.DTO.Message.MessageDTO;
import com.alibou.alibou.DTO.Message.MessageHistoryDTO;
import com.alibou.alibou.Model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final IMessageService messageService;
    @Autowired
    public MessageController(IMessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/createMessage")
    public ResponseEntity<String> createMessage(@RequestBody MessageDTO messageDTO) {
        try {
            messageService.createMessage(messageDTO);
            return ResponseEntity.ok("Message created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create message: " + e.getMessage());
        }
    }

    @PostMapping("/messageHistory")
    public ResponseEntity<List<Message>> getMessageHistory(@RequestBody MessageHistoryDTO requestDTO) {
        try {
            int senderId = requestDTO.getSenderId();
            int receiverId = requestDTO.getReceiverId();
            int page = requestDTO.getPage();
            int size = requestDTO.getSize();

            if (page < 0 || size < 1) {
                throw new IllegalArgumentException("Invalid page or size value.");
            }

            List<Message> messageHistory = messageService.getMessageHistory(senderId, receiverId, page, size);

            return ResponseEntity.ok(messageHistory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/deleteAllMessages")
    public ResponseEntity<String> deleteAllMessages(@RequestBody DeleteAllMessagesDTO request) {
        try {
            messageService.deleteAllMessages(request);
            return ResponseEntity.ok("All messages deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete all messages: " + e.getMessage());
        }
    }

}
