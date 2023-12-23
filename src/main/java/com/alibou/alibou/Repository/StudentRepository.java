package com.alibou.alibou.Repository;

import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository  extends JpaRepository<Student, Integer> {
    Optional<Student> findByUser(User user);
    @Query("SELECT s FROM Student s WHERE s.teacher.teacher_id = :teacherId")
    List<Student> findStudentsByTeacherId(@Param("teacherId") int teacherId);

    @Query("SELECT s FROM Student s WHERE s.user.user_id= :userId")
    Optional<Student> findByUserId(int userId);
}
