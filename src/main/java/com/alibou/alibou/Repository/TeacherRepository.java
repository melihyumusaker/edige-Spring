package com.alibou.alibou.Repository;

import com.alibou.alibou.Model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    @Query("SELECT s FROM Teacher s WHERE s.user.user_id= :userId")
    Optional<Teacher> findByUserId(int userId);
}
