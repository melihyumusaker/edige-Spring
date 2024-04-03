package com.alibou.alibou.Core.IServices;

import com.alibou.alibou.Model.Lesson;

import java.util.List;

public interface ILessonService {
    List<String> findDistinctGrades();
    List<String> findLessonNamesByGrade(String grade);
    List<String> findSublessonNamesByGradeAndLessonName(String grade, String lessonName);
    List<String> findSublessonNameDetailsByGradeAndLessonNameAndSublessonName(String grade, String lessonName, String sublessonName);

    List<Lesson> getAllLessons();
}
