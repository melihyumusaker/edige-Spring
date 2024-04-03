package com.alibou.alibou.Controller;

import com.alibou.alibou.Core.IServices.ILessonService;
import com.alibou.alibou.DTO.Lesson.GetLessonNamesByGradeDTO;
import com.alibou.alibou.DTO.Lesson.GetSublessonNameDetailsByGradeAndLessonNameAndSublessonNameDTO;
import com.alibou.alibou.DTO.Lesson.GetSublessonNamesByGradeAndLessonNameDTO;
import com.alibou.alibou.Model.Lesson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lessons")
@CacheConfig(cacheNames = "lessons")
public class LessonController {

    private static final Logger logger = LoggerFactory.getLogger(LessonController.class);

    private final ILessonService lessonService;

    @Autowired
    public LessonController(ILessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping(path = "/getAllLessons", produces = "application/json;charset=UTF-8")
    @Cacheable(key = "'allLessons'")
    public ResponseEntity<?> getAllLessons(){
        try {
            List<Lesson> allDatas = lessonService.getAllLessons();
            return new ResponseEntity<>(allDatas , HttpStatus.OK);
        }catch (Exception e){
            logger.error("Hata meydana geldi", e);
            return new ResponseEntity<>("Failed to retrieve grades. " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/grades", produces = "application/json;charset=UTF-8")
    @Cacheable(key = "'distinctGrades'")
    public ResponseEntity<?> getDistinctGrades() {
        try {
            List<String> grades = lessonService.findDistinctGrades();
            return new ResponseEntity<>(grades, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving distinct grades", e);
            return new ResponseEntity<>("Failed to retrieve grades.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/lessonNames", produces = "application/json;charset=UTF-8")
    @Cacheable(key = "'LessonNames'")
    public ResponseEntity<?> getLessonNamesByGrade(@RequestBody GetLessonNamesByGradeDTO request) {
        try {
            List<String> lessonNames = lessonService.findLessonNamesByGrade(request.getGrade());
            return new ResponseEntity<>(lessonNames, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving lesson names by grade", e);
            return new ResponseEntity<>("Failed to retrieve lesson names for grade.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/sublessonNames", produces = "application/json;charset=UTF-8")
    @Cacheable(key = "'SublessonNames'")
    public ResponseEntity<?> getSublessonNamesByGradeAndLessonName(@RequestBody GetSublessonNamesByGradeAndLessonNameDTO request) {
        try {
            List<String> sublessonNames = lessonService.findSublessonNamesByGradeAndLessonName(request.getGrade(), request.getLessonName());
            return new ResponseEntity<>(sublessonNames, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving sublesson names", e);
            return new ResponseEntity<>("Failed to retrieve sublesson names.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/sublessonNameDetails", produces = "application/json;charset=UTF-8")
    @Cacheable(key = "'SublessonNameDetails'")
    public ResponseEntity<?> getSublessonNameDetailsByGradeAndLessonNameAndSublessonName(@RequestBody GetSublessonNameDetailsByGradeAndLessonNameAndSublessonNameDTO request) {
        try {
            List<String> sublessonDetails = lessonService.findSublessonNameDetailsByGradeAndLessonNameAndSublessonName(request.getGrade(), request.getLessonName(), request.getSublessonName());
            return new ResponseEntity<>(sublessonDetails, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving sublesson name details", e);
            return new ResponseEntity<>("Failed to retrieve sublesson name details.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
