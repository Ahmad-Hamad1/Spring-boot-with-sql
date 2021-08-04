package com.example.demo.services;

import com.example.demo.entities.Course;
import com.example.demo.entities.Student;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.student.StudentDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServices {
    private final ModelMapper modelMapper;
    private StudentRepository studentRepository;
    private CourseServices courseServices;

    public StudentServices(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Autowired
    public void setCourseServices(CourseServices courseServices) {
        this.courseServices = courseServices;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public List<StudentDto> getAllFullName() {
        return
                studentRepository.findAll().stream()
                        .map(student -> modelMapper.map(student, StudentDto.class))
                        .collect(Collectors.toList());
    }

    public Page<Student> getStudentsPaged(int pageNumber, int pageSize,
                                          String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize,
                Sort.by(sortBy).ascending());
        return studentRepository.findAll(pageable);
    }

    public Student insertStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        studentOptional.ifPresent(student -> {
            studentRepository.delete(student);
        });
        studentOptional.orElseThrow(() -> new NoSuchElementException("No student with this Id"));
    }

    public Student updateStudent(Long id, Student student) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) {
            student.setID(id);
            return studentRepository.save(student);
        } else
            throw new NoSuchElementException("No student with this Id");
    }

    public void addCourse(long studentId, long courseID) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        Optional<Course> optionalCourse = courseServices.getCourse(courseID);
        if (studentOptional.isEmpty())
            throw new NoSuchElementException("No student with this Id");
        if (optionalCourse.isEmpty())
            throw new NoSuchElementException("No course with this Id");
        studentOptional.get().getCourses().add(optionalCourse.get());
    }

    public List<Student> getStudentsByAge(Integer age) {
        return studentRepository.getStudentsByAge(age);
    }

    public void deleteCourseForStudent(long studentID, long courseId) {
        Optional<Student> studentOptional = studentRepository.findById(studentID);
        Optional<Course> courseOptional = courseServices.getCourse(courseId);
        if (studentOptional.isEmpty())
            throw new NoSuchElementException("No student with this Id");
        if (courseOptional.isEmpty())
            throw new NoSuchElementException("No course with this Id");
        if (!studentOptional.get().getCourses().contains(courseOptional.get()))
            throw new NoSuchElementException("The given student is not enrolled in the given course");
        studentOptional.get().getCourses().remove(courseOptional.get());
    }

    public void removeCourseFromAll(long ID) {
        List<Student> studentList = studentRepository.findAll();
        for (Student student : studentList) {
            for (Course course : student.getCourses()) {
                if (course.getId() == ID)
                    student.getCourses().remove(course);
            }
        }
    }

    public Student getStudentById(long id) {
        return studentRepository.findById(id).orElseThrow();
    }

    public Student getStudentByEmail(String email) {
        return studentRepository.getStudentByEmail(email);
    }
}
