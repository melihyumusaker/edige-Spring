package com.alibou.alibou.Repository;

import com.alibou.alibou.Model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    @Query("SELECT s FROM Teacher s WHERE s.user.user_id= :userId")
    Optional<Teacher> findByUserId(int userId);

    @Query("SELECT t FROM Teacher t WHERE t.student_number < 15 AND (" +
            "((:studentType = 'Tip 1') AND t.enneagram_result IN ('Tip 2', 'Tip 6', 'Tip 9', 'Tip 3')) OR " +
            "((:studentType = 'Tip 2') AND t.enneagram_result IN ('Tip 1', 'Tip 9', 'Tip 6', 'Tip 3')) OR " +
            "((:studentType = 'Tip 3') AND t.enneagram_result IN ('Tip 2', 'Tip 1', 'Tip 3', 'Tip 7')) OR " +
            "((:studentType = 'Tip 4') AND t.enneagram_result IN ('Tip 2', 'Tip 9', 'Tip 5', 'Tip 7')) OR " +
            "((:studentType = 'Tip 5') AND t.enneagram_result IN ('Tip 4', 'Tip 6', 'Tip 1', 'Tip 7')) OR " +
            "((:studentType = 'Tip 6') AND t.enneagram_result IN ('Tip 2', 'Tip 5', 'Tip 1', 'Tip 7')) OR " +
            "((:studentType = 'Tip 7') AND t.enneagram_result IN ('Tip 2', 'Tip 3', 'Tip 4', 'Tip 5')) OR " +
            "((:studentType = 'Tip 8') AND t.enneagram_result IN ('Tip 2', 'Tip 3', 'Tip 5', 'Tip 6')) OR " +
            "((:studentType = 'Tip 9') AND t.enneagram_result IN ('Tip 1', 'Tip 2', 'Tip 3', 'Tip 6')))")
    List<Teacher> findTeachersByStudentType(@Param("studentType") String studentType);

    @Query("SELECT t FROM Teacher t WHERE t.student_number < 15 ORDER BY FUNCTION('RAND')")
    List<Teacher> findRandomTeachersWithLessThan15Students();

    default List<Teacher> findRecommendedTeachers(String studentType) {
        List<Teacher> teachers = findTeachersByStudentType(studentType);
        if (teachers.isEmpty()) {
            teachers = findRandomTeachersWithLessThan15Students();
            if (teachers.size() > 5) {
                return teachers.subList(0, 5);
            }
        }
        return teachers;
    }

}
