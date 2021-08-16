package com.example.demo.services;

import com.example.demo.dtos.StudentDto;
import com.example.demo.entities.Course;
import com.example.demo.entities.Student;
import com.example.demo.repositories.CourseRepository;
import com.example.demo.repositories.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServices {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModelMapper mapper;

    public List<StudentDto> getStudents() {
        return studentRepository.findAll()
                .stream().map(student -> {
                    return mapper.map(student, StudentDto.class);
                })
                .collect(Collectors.toList());
    }

    public List<StudentDto> getAllFullName() {
        return
                studentRepository.findAll().stream()
                        .map(student -> mapper.map(student, StudentDto.class))
                        .toList();
    }

    public Page<Student> getStudentsPaged(int pageNumber, int pageSize,
                                          String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize,
                Sort.by(sortBy).ascending());
        return studentRepository.findAll(pageable);
    }

    @Async
    public CompletableFuture<StudentDto> insertStudent(StudentDto studentDto) {
        return CompletableFuture.completedFuture(mapper.map(studentRepository.
                save(mapper.map(studentDto, Student.class)), StudentDto.class));
    }

    @Async
    @CacheEvict(value = "student-cache", key = "'Student-Cache'+#id")
    public CompletableFuture<StudentDto> deleteStudent(Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        studentOptional.ifPresent(studentRepository::delete);
        studentOptional.orElseThrow(() -> new NoSuchElementException("No student with this Id"));
        return CompletableFuture.completedFuture(null);
    }

    @Async
    @CachePut(value = "student-cache", key = "'Student-Cache'+#id")
    public CompletableFuture<StudentDto> updateStudent(Long id, StudentDto studentDto) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) {
            studentDto.setId(id);
            return CompletableFuture
                    .completedFuture(mapper.map(studentRepository.
                            save(mapper.map(studentDto, Student.class)), StudentDto.class));
        } else
            throw new NoSuchElementException("No student with this Id");
    }

    @Async
    public void addCourse(long studentId, long courseID) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        Optional<Course> courseOptional = courseRepository.findById(courseID);
        if (studentOptional.isEmpty())
            throw new NoSuchElementException("No student with this Id");
        if (courseOptional.isEmpty())
            throw new NoSuchElementException("No course with this Id");
        studentOptional.get().getCourses().add(courseOptional.get());
    }

    public List<StudentDto> getStudentsByAge(Integer age) {
        return studentRepository.getStudentsByAge(age)
                .stream().map(student -> {
                    return mapper.map(student, StudentDto.class);
                })
                .collect(Collectors.toList());
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

    public void removeCourseFromAll(long id) {
        List<Student> studentList = studentRepository.findAll();
        for (Student student : studentList) {
            for (Course course : student.getCourses()) {
                if (course.getId() == id)
                    student.getCourses().remove(course);
            }
        }
    }

    @Async
    @Cacheable(value = "student-cache", key = "'Student-Cache'+#id")
    public CompletableFuture<StudentDto> getStudentById(long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isEmpty())
            throw new NoSuchElementException("No student with this Id");
        return CompletableFuture.completedFuture(mapper.map(studentOptional.get(), StudentDto.class));
    }

    @Async
    public CompletableFuture<StudentDto> getStudentByEmail(String email) {
        Student student = studentRepository.getStudentByEmail(email);
        if (student == null) {
            throw new NoSuchElementException("No Student with this Id");
        }
        return CompletableFuture.completedFuture(mapper.map(student, StudentDto.class));
    }
}
