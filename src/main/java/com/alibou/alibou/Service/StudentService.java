package com.alibou.alibou.Service;

import com.alibou.alibou.Core.IServices.IStudentService;
import com.alibou.alibou.DTO.Relation.SetRelationDTO;
import com.alibou.alibou.DTO.Student.GetAllStudentResponseDTO;
import com.alibou.alibou.DTO.Teacher.GetAllTeacherDTO;
import com.alibou.alibou.DTO.Teacher.UpdateTeacherDTO;
import com.alibou.alibou.Model.Relation;
import com.alibou.alibou.Model.Student;
import com.alibou.alibou.Model.Teacher;
import com.alibou.alibou.Model.User;
import com.alibou.alibou.Repository.RelationRepository;
import com.alibou.alibou.Repository.StudentRepository;
import com.alibou.alibou.Repository.TeacherRepository;
import com.alibou.alibou.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class StudentService implements IStudentService {
    private final StudentRepository studentRepository;
    private final RelationRepository relationRepository;
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;

    @Autowired
    public StudentService(TeacherRepository teacherRepository,StudentRepository studentRepository,RelationRepository relationRepository
    ,UserRepository userRepository){
        this.studentRepository = studentRepository;
        this.relationRepository = relationRepository;
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
    }

    public List<GetAllStudentResponseDTO> getAllStudents(){
        List<Student> students = studentRepository.findAll();
        List<GetAllStudentResponseDTO> response = students.stream().map(student -> GetAllStudentResponseDTO.builder()
                        .student_id(student.getStudent_id())
                .name(student.getUser().getName())
                .surname(student.getUser().getSurname())
                .birth_date(student.getUser().getBirth_date())
                .email(student.getUser().getEmail())
                .phone(student.getUser().getPhone())
                .city(student.getUser().getCity())
                .enneagram_result(student.getEnneagram_result())
                .section(student.getSection())
                .school(student.getSchool())
                .coachName(student.getTeacher().getUser().getName() + " " + student.getTeacher().getUser().getSurname()).build())
                .collect(Collectors.toList());
        return response;
    }

    @Override
    public Student getStudentDetailsById(int studentId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return null;
        }else{
            return student;
        }
    }
    @Override
    public Relation postRelation(int teacher_id , int studentId) {

        // Relation tablesine veri ekleme
        Relation relation = new Relation();

        Student student = new Student();
        student.setStudent_id(studentId);

        Teacher teacher = new Teacher();
        teacher.setTeacher_id(teacher_id);

        relation.setStudent_id(student);
        relation.setTeacher_id(teacher);

        relationRepository.save(relation);

        // Student tablesini güncelleme
        Optional<Teacher> teacher1 = teacherRepository.findById(teacher_id);
        Optional<Student> student1 = studentRepository.findById(studentId);

        if (student1.isPresent() && teacher1.isPresent()) {
            Student existingStudent = student1.get();
            existingStudent.setTeacher(teacher1.get());
            studentRepository.save(existingStudent);
        }
        return relation;
    }

    public int getStudentIdByUserId(int userId) {
        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Öğrenci bulunamadı: " + userId));

        return student.getStudent_id();
    }

    public Student getStudentByUserId(int userId) {
        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Öğrenci bulunamadı: " + userId));

        if(student != null){
            return student;
        }else{
            return null;
        }

    }

    public void setEnneagramTestSolved(int studentId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.setIs_enneagram_test_solved(1);
            studentRepository.save(student);
        } else {
            throw new EntityNotFoundException("Student not found with ID: " + studentId);
        }
    }

    @Override
    public boolean updateStudent(GetAllStudentResponseDTO request) {
        Optional<Student> optionalStudent = studentRepository.findById(request.getStudent_id());

        if (optionalStudent.isPresent()) {
            Student existingStudent = optionalStudent.get();
            User user = existingStudent.getUser();

            updateStudentFields(existingStudent, request);
            updateUserFields(user, request);

            studentRepository.save(existingStudent);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    private void updateStudentFields(Student student, GetAllStudentResponseDTO request) {
        updateIfNotNull(request.getEnneagram_result(), student::setEnneagram_result);
        updateIfNotNull(request.getSection(), student::setSection);
        updateIfNotNull(request.getSchool(), student::setSchool);
    }

    private void updateUserFields(User user, GetAllStudentResponseDTO request) {
        updateIfNotNull(request.getName(), user::setName);
        updateIfNotNull(request.getSurname(), user::setSurname);
        updateIfNotNull(request.getBirth_date(), user::setBirth_date);
        updateIfNotNull(request.getEmail(), user::setEmail);
        updateIfNotNull(request.getPhone(), user::setPhone);
        updateIfNotNull(request.getCity(), user::setCity);
        updateIfNotNull(request.getUsername(), user::setUsername);
    }

    private <T> void updateIfNotNull(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }

}
