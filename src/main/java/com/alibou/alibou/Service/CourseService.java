package com.alibou.alibou.Service;
import com.alibou.alibou.Core.IServices.ICourseService;
import com.alibou.alibou.DTO.StudentCourse.StudentFinishHomeworkDTO;
import com.alibou.alibou.Model.Course;
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
}
