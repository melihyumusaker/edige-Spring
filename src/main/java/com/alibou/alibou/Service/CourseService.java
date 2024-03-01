package com.alibou.alibou.Service;
import com.alibou.alibou.Core.IServices.ICourseService;
import com.alibou.alibou.DTO.Course.AddNewCourseDTO;
import com.alibou.alibou.DTO.StudentCourse.StudentFinishHomeworkDTO;
import com.alibou.alibou.Model.Course;
import com.alibou.alibou.Model.Meeting;
import com.alibou.alibou.Repository.CourseRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CourseService implements ICourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    public void studentFinishHomework(StudentFinishHomeworkDTO request) {
        try {
            courseRepository.updateHomeworkStatus(request.getCourse_id(), request.getIs_homework_done(), request.getStudent_comment());
        } catch (Exception ex) {
            throw new RuntimeException("Error while updating homework status");
        }
    }

    @Override
    public int addNewCourse(AddNewCourseDTO request) {
        try {
            Course course = Course.builder()
                    .course_name(request.getCourse_name())
                    .subcourse_name(request.getSubcourse_name())
                    .student_comment("")
                    .homework_description(request.getHomework_description())
                    .is_homework_done(0)
                    .homework_deadline(request.getHomework_deadline())
                    .build();

            courseRepository.save(course);

            return course.getCourse_id();
        } catch (Exception e) {
            throw new RuntimeException("Course could not be added. Reason: " + e.getMessage());
        }
    }

}
