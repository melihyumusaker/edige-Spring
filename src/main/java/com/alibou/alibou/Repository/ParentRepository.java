package com.alibou.alibou.Repository;

import com.alibou.alibou.Model.Parent;

import com.alibou.alibou.Model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ParentRepository extends JpaRepository<Parent, Integer> {
    @Query("SELECT s FROM Parent s WHERE s.user.user_id= :userId")
    Optional<Parent> findByUserId(int userId);

}
