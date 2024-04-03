package com.alibou.alibou.DTO.Lesson;

import lombok.Data;

@Data
public class GetSublessonNameDetailsByGradeAndLessonNameAndSublessonNameDTO {
    private String grade;
    private String lessonName;
    private String sublessonName;
}
