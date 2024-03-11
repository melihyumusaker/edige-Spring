package com.alibou.alibou.Repository;

import com.alibou.alibou.Model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository  extends JpaRepository<Message, Integer> {


    @Query("SELECT m FROM Message m WHERE (m.sender_id = :senderId1 AND m.receiver_id = :receiverId1) OR (m.sender_id = :senderId2 AND m.receiver_id = :receiverId2) ORDER BY m.send_date DESC")
    List<Message> findMessagesBySenderReceiverIds(@Param("senderId1") int senderId1, @Param("receiverId1") int receiverId1, @Param("senderId2") int senderId2, @Param("receiverId2") int receiverId2);

    @Query("SELECT m FROM Message m WHERE (m.sender_id = :senderId1 AND m.receiver_id = :receiverId1) OR (m.sender_id = :senderId2 AND m.receiver_id = :receiverId2)")
    List<Message> findBySenderIdAndReceiverIdOrSenderIdAndReceiverId(@Param("senderId1") int senderId1, @Param("receiverId1") int receiverId1, @Param("senderId2") int senderId2, @Param("receiverId2") int receiverId2);

}
