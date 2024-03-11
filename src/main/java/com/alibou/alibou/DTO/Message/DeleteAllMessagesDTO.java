package com.alibou.alibou.DTO.Message;

import lombok.Data;

@Data
public class DeleteAllMessagesDTO {
    private int receiver_id;
    private int sender_id;
}
