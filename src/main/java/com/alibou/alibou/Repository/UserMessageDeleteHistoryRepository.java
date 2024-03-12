package com.alibou.alibou.Repository;

import com.alibou.alibou.Model.UserMessageDeleteHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserMessageDeleteHistoryRepository extends JpaRepository<UserMessageDeleteHistory, Integer> {
    @Query("SELECT umdh FROM UserMessageDeleteHistory umdh " +
            "WHERE (umdh.user1_id = :receiverId AND umdh.user2_id = :senderId) " +
            "OR (umdh.user1_id = :senderId AND umdh.user2_id = :receiverId)")
    UserMessageDeleteHistory findByUser1_idAndUser2_idOrUser1_idAndUser2_id(
            @Param("receiverId") int receiverId, @Param("senderId") int senderId);


    @Query("SELECT umdh.user2_id FROM UserMessageDeleteHistory umdh WHERE umdh.user1_id = :userId AND umdh.is_user1_deleted = false " +
            "UNION " +
            "SELECT umdh.user1_id FROM UserMessageDeleteHistory umdh WHERE umdh.user2_id = :userId AND umdh.is_user2_deleted = false")
    List<Integer> findConnectedUserIds(@Param("userId") int userId);
}
