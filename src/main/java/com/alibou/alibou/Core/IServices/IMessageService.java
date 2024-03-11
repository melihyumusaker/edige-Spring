package com.alibou.alibou.Core.IServices;

import com.alibou.alibou.DTO.Message.DeleteAllMessagesDTO;
import com.alibou.alibou.DTO.Message.MessageDTO;
import com.alibou.alibou.Model.Message;
import com.alibou.alibou.Model.Relation;

import java.util.List;

public interface IMessageService {

    void createMessage(MessageDTO messageDTO);

    List<Message> getMessageHistory(int senderId, int receiverId, int page, int size);

    void deleteAllMessages(DeleteAllMessagesDTO request);
}
