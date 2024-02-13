    package com.alibou.alibou.DTO.Teacher;

    import lombok.Data;

    @Data
    public class UpdateTeacherEnneagramTypeAndAboutDTO {
        private String about;
        private String enneagram_result;
        private int is_enneagram_test_solved;
    }
