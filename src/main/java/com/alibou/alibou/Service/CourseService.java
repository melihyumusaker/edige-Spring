package com.alibou.alibou.Service;
import com.alibou.alibou.Core.IServices.ICourseService;
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
}
