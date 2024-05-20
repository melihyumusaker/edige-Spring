package com.alibou.alibou.Service;
import com.alibou.alibou.Core.IServices.ICourseService;
import com.alibou.alibou.DTO.Course.AddNewCourseDTO;
import com.alibou.alibou.DTO.Course.DeleteCourseDTO;
import com.alibou.alibou.DTO.Course.UpdateCourseDTO;
import com.alibou.alibou.DTO.StudentCourse.StudentFinishHomeworkDTO;
import com.alibou.alibou.Model.Course;
import com.alibou.alibou.Model.Meeting;
import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Model.WeeklyProgram;
import com.alibou.alibou.Repository.CourseRepository;
import com.alibou.alibou.Repository.StudentCourseRepository;
import com.alibou.alibou.Repository.StudentRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class CourseService implements ICourseService {
    private final CourseRepository courseRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final StudentRepository studentRepository;

    public CourseService(CourseRepository courseRepository,StudentCourseRepository studentCourseRepository,StudentRepository studentRepository){
        this.studentCourseRepository = studentCourseRepository;
        this.studentRepository = studentRepository;
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
                    .is_shown(0)
                    .build();

            courseRepository.save(course);
            return course.getCourse_id();
        } catch (Exception e) {
            throw new RuntimeException("Course could not be added. Reason: " + e.getMessage());
        }
    }
    public boolean updateCourse(UpdateCourseDTO request) {
        Optional<Course> optionalCourse = courseRepository.findById(request.getCourse_id());

        optionalCourse.ifPresent(existingCourse -> {
            updateIfNotNull(request.getCourse_name(), existingCourse::setCourse_name);
            updateIfNotNull(request.getSubcourse_name(), existingCourse::setSubcourse_name);
            updateIfNotNull(request.getHomework_description(), existingCourse::setHomework_description);
            updateIfNotNull(request.getHomework_deadline(), existingCourse::setHomework_deadline);
            updateIfNotNull(request.getIs_homework_done(), existingCourse::setIs_homework_done);
            updateIfNotNull(request.getIs_shown() , existingCourse::setIs_shown);
            courseRepository.save(existingCourse);
        });

        return optionalCourse.isPresent();
    }

    private <T> void updateIfNotNull(T newValue, Consumer<T> setter) {
        if (newValue != null) {
            setter.accept(newValue);
        }
    }

    @Override
    public void deleteCourse(DeleteCourseDTO request) {
        int courseId = request.getCourse_id();
        int studentId = request.getStudent_id();

        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        Optional<Student> optionalStudent = studentRepository.findById(studentId);

        try {
            if (optionalCourse.isPresent() && optionalStudent.isPresent()) {
                studentCourseRepository.deleteByCourseIdAndStudentId(courseId, studentId);
                courseRepository.deleteById(courseId);
            } else {
                throw new NoSuchElementException("Course veya Student bulunamadı.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }



}
