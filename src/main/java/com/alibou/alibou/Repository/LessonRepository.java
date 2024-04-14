package com.alibou.alibou.Repository;

import com.alibou.alibou.Model.Lesson;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LessonRepository  extends JpaRepository<Lesson, Integer> {

    @Query("SELECT DISTINCT l.grade FROM Lesson l")
    List<String> findDistinctGrades();

    @Query("SELECT DISTINCT l.lesson_name FROM Lesson l WHERE l.grade = ?1")
    List<String> findLessonNamesByGrade(String grade);

    @Query("SELECT DISTINCT l.sublesson_name FROM Lesson l WHERE l.grade = ?1 AND l.lesson_name = ?2")
    List<String> findSublessonNamesByGradeAndLessonName(String grade, String lessonName);

    @Query("SELECT DISTINCT l.sublesson_name_detail FROM Lesson l WHERE l.grade = ?1 AND l.lesson_name = ?2 AND l.sublesson_name = ?3")
    List<String> findSublessonNameDetailsByGradeAndLessonNameAndSublessonName(String grade, String lessonName, String sublessonName);

}
