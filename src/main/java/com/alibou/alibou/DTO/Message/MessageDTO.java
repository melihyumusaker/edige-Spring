package com.alibou.alibou.DTO.Message;

import lombok.Data;

@Data
public class MessageDTO {
    private int senderId;
    private int receiverId;
    private String messageContent;
}
