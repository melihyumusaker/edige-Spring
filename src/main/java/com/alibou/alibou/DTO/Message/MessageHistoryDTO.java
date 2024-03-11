package com.alibou.alibou.DTO.Message;

import lombok.Data;

@Data
public class MessageHistoryDTO {
    private int senderId;
    private int receiverId;
    private int page;
    private int size;
}
