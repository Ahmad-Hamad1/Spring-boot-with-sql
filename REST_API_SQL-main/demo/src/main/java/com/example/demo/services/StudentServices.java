package com.example.demo.services;

import com.example.demo.entities.Course;
import com.example.demo.entities.Student;
import com.example.demo.repositories.CourseRepository;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.student.StudentDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServices {
    private final ModelMapper modelMapper;
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;

    public StudentServices(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Autowired
    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
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

    @Async
    public CompletableFuture<Student> insertStudent(Student student) {
        return CompletableFuture.completedFuture(studentRepository.save(student));
    }

    @Async
    public CompletableFuture<Student> deleteStudent(Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        studentOptional.ifPresent(student -> {
            studentRepository.delete(student);
        });
        studentOptional.orElseThrow(() -> new NoSuchElementException("No student with this Id"));
        return null;
    }

    @Async
    public CompletableFuture<Student> updateStudent(Long id, Student student) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) {
            student.setID(id);
            return CompletableFuture.completedFuture(studentRepository.save(student));
        } else
            throw new NoSuchElementException("No student with this Id");
    }

    @Async
    public void addCourse(long studentId, long courseID) throws ExecutionException, InterruptedException {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        Optional<Course> courseOptional = courseRepository.findById(courseID);
        if (studentOptional.isEmpty())
            throw new NoSuchElementException("No student with this Id");
        if (courseOptional.isEmpty())
            throw new NoSuchElementException("No course with this Id");
        studentOptional.get().getCourses().add(courseOptional.get());
    }

    public List<Student> getStudentsByAge(Integer age) {
        return studentRepository.getStudentsByAge(age);
    }

    public void deleteCourseForStudent(long studentID, long courseId) {
        Optional<Student> studentOptional = studentRepository.findById(studentID);
        Optional<Course> courseOptional = courseRepository.findById(courseId);
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

    @Async
    public CompletableFuture<Student> getStudentById(long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isEmpty())
            throw new NoSuchElementException("No student with this Id");
        return CompletableFuture.completedFuture(studentOptional.get());
    }

    @Async
    public CompletableFuture<Student> getStudentByEmail(String email, boolean isTest) {
        Optional<Student> studentOptional = studentRepository.getStudentByEmail(email);
        if(isTest) {
            if(studentOptional.isPresent())
                return CompletableFuture.completedFuture(studentOptional.get());
            return null;
        }
        if (studentOptional.isEmpty())
            throw new NoSuchElementException("No student with this email");
        return CompletableFuture.completedFuture(studentOptional.get());
    }
}
