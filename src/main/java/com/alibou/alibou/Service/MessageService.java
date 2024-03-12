package com.alibou.alibou.Service;

import com.alibou.alibou.Core.IServices.IMessageService;
import com.alibou.alibou.DTO.Message.DeleteAllMessagesDTO;
import com.alibou.alibou.DTO.Message.MessageDTO;
import com.alibou.alibou.DTO.User.KullaniciDTO;
import com.alibou.alibou.Model.Message;
import com.alibou.alibou.Model.User;
import com.alibou.alibou.Model.UserMessageDeleteHistory;
import com.alibou.alibou.Repository.MessageRepository;
import com.alibou.alibou.Repository.UserMessageDeleteHistoryRepository;
import com.alibou.alibou.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageService implements IMessageService {
    private final MessageRepository messageRepository;
    private final UserMessageDeleteHistoryRepository userMessageDeleteHistoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, UserMessageDeleteHistoryRepository userMessageDeleteHistoryRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userMessageDeleteHistoryRepository = userMessageDeleteHistoryRepository;
        this.userRepository = userRepository;
    }


    @Override
    public void createMessage(MessageDTO messageDTO) {

        Message message = Message.builder()
                .sender_id(messageDTO.getSenderId())
                .receiver_id(messageDTO.getReceiverId())
                .message_content(messageDTO.getMessageContent())
                .send_date(new Date())
                .build();

        UserMessageDeleteHistory messageDeleteHistory =
                userMessageDeleteHistoryRepository.findByUser1_idAndUser2_idOrUser1_idAndUser2_id(messageDTO.getSenderId(), messageDTO.getReceiverId());

        if (messageDeleteHistory == null) {
            messageDeleteHistory = UserMessageDeleteHistory.builder()
                    .user1_id(messageDTO.getSenderId())
                    .user2_id(messageDTO.getReceiverId())
                    .is_user1_deleted(false)
                    .is_user2_deleted(false)
                    .build();

            userMessageDeleteHistoryRepository.save(messageDeleteHistory);
        }else{
            messageDeleteHistory.set_user1_deleted(false);
            messageDeleteHistory.set_user2_deleted(false);
            userMessageDeleteHistoryRepository.save(messageDeleteHistory);
        }

        messageRepository.save(message);
    }

    @Override
    public List<Message> getMessageHistory(int senderId, int receiverId, int page, int size) {

        UserMessageDeleteHistory messageDeleteHistory =
                userMessageDeleteHistoryRepository.findByUser1_idAndUser2_idOrUser1_idAndUser2_id(receiverId, senderId);

        if(senderId == messageDeleteHistory.getUser1_id()){
            if(messageDeleteHistory.is_user1_deleted()) return null;
            else{
                List<Message> messageHistory = messageRepository.findMessagesBySenderReceiverIds(
                        senderId, receiverId, receiverId, senderId);

                int startIndex = page * size;
                int endIndex = Math.min(startIndex + size, messageHistory.size());
                return messageHistory.subList(startIndex, endIndex);
            }
        }

        else if(senderId == messageDeleteHistory.getUser2_id()){
            if(messageDeleteHistory.is_user2_deleted()) return null;
            else{
                List<Message> messageHistory = messageRepository.findMessagesBySenderReceiverIds(
                        senderId, receiverId, receiverId, senderId);

                int startIndex = page * size;
                int endIndex = Math.min(startIndex + size, messageHistory.size());
                return messageHistory.subList(startIndex, endIndex);
            }
        }

        else return null;

    }

    @Override
    public void deleteAllMessages(DeleteAllMessagesDTO request) {
        int receiverId = request.getReceiver_id();
        int senderId = request.getSender_id();

        // receiver_id ve sender_id'ye sahip kaydı bul
        UserMessageDeleteHistory messageDeleteHistory =
                userMessageDeleteHistoryRepository.findByUser1_idAndUser2_idOrUser1_idAndUser2_id(receiverId, senderId);

        // Kayıt bulunamadıysa işlem yapma
        if (messageDeleteHistory == null) return;
        if (senderId == messageDeleteHistory.getUser1_id())  messageDeleteHistory.set_user1_deleted(true);
        else if (senderId == messageDeleteHistory.getUser2_id()) messageDeleteHistory.set_user2_deleted(true);

        userMessageDeleteHistoryRepository.save(messageDeleteHistory);

        if(messageDeleteHistory.is_user1_deleted() && messageDeleteHistory.is_user2_deleted()){
            userMessageDeleteHistoryRepository.deleteById(messageDeleteHistory.getId());

            List<Message> messagesToDelete =
                    messageRepository.findBySenderIdAndReceiverIdOrSenderIdAndReceiverId(senderId, receiverId,receiverId ,senderId  );
            messageRepository.deleteAll(messagesToDelete);
        }
    }

    @Override
    public  List<KullaniciDTO> findConnectedUserIds(int userId) {
        List<Integer> connectedUserIds = userMessageDeleteHistoryRepository.findConnectedUserIds(userId);
         List<User> users = userRepository.findByUserIdsIn(connectedUserIds);
        List<KullaniciDTO> kullaniciDTOList = new ArrayList<>();
        for (User user : users) {
            kullaniciDTOList.add(mapToKullaniciDTO(user));
        }
        return kullaniciDTOList;

    }

    private KullaniciDTO mapToKullaniciDTO(User user) {
        return KullaniciDTO.builder()
                .user_id(user.getUserId())
                .username(user.getUsername())
                .password(user.getPassword())
                .name(user.getName())
                .surname(user.getSurname())
                .birth_date(user.getBirth_date())
                .email(user.getEmail())
                .phone(user.getPhone())
                .city(user.getCity())
                .role(user.getRole())
                .is_active(user.getIs_active())
                .build();
    }
}
