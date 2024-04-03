package com.alibou.alibou.Service;

import com.alibou.alibou.Core.IServices.ILessonService;
import com.alibou.alibou.Model.Lesson;
import com.alibou.alibou.Repository.LessonRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Cacheable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService implements ILessonService {


    private final LessonRepository lessonRepository;
    private final CaffeineCacheManager cacheManager;

    @Autowired
    private LessonService(LessonRepository lessonRepository,CaffeineCacheManager cacheManager){
        this.lessonRepository = lessonRepository;
        this.cacheManager = cacheManager;
    }



    @Override

    public List<String> findDistinctGrades() {
        return lessonRepository.findDistinctGrades();
    }

    @Override
    public List<String> findLessonNamesByGrade(String grade) {
        return lessonRepository.findLessonNamesByGrade(grade);
    }


    @Override
    public List<String> findSublessonNamesByGradeAndLessonName(String grade, String lessonName) {
        return lessonRepository.findSublessonNamesByGradeAndLessonName(grade, lessonName);
    }

    @Override
    public List<String> findSublessonNameDetailsByGradeAndLessonNameAndSublessonName(String grade, String lessonName, String sublessonName) {
        return lessonRepository.findSublessonNameDetailsByGradeAndLessonNameAndSublessonName(grade, lessonName, sublessonName);
    }

    @Override
    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }


}
