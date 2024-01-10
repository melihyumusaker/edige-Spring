package com.alibou.alibou.Repository;

import com.alibou.alibou.Core.Roles.Role;
import com.alibou.alibou.Model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    User findByRole(Role role);
    Optional<User> findFirstByUsername (String username);
    @Query("SELECT u FROM User u WHERE u.is_active = 1")
    List<User> findAllActiveUsers();

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.is_active = 0 WHERE u.user_id = :userId")
    void deactivateUserById(@Param("userId") int userId);

    @Modifying
    @Query("UPDATE User u SET " +
            "u.name = COALESCE(:#{#updatedUser.name}, u.name), " +
            "u.surname = COALESCE(:#{#updatedUser.surname}, u.surname), " +
            "u.email = COALESCE(:#{#updatedUser.email}, u.email), " +
            "u.phone = COALESCE(:#{#updatedUser.phone}, u.phone), " +
            "u.city = COALESCE(:#{#updatedUser.city}, u.city) " +
            "WHERE u.user_id = :userId")
    void updateUser(@Param("userId") int userId, @Param("updatedUser") User updatedUser);

}
