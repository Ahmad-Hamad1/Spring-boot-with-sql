package com.example.demo.services;

import com.example.demo.entities.Course;
import com.example.demo.entities.Student;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.student.StudentDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
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

    public Page<Student> getStudentsPaged(int pageNumber, int pageSize,
                                          String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize,
                Sort.by(sortBy).ascending());
        return studentRepository.findAll(pageable);
    }

    public void insertStudent(Student student) {
        studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        try {
            studentRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e);
        }
    }

    public void updateStudent(Long id, Student student) {
        student.setID(id);
        if (!studentRepository.findById(id).isEmpty())
            studentRepository.save(student);
    }

    public void addCourse(long studentId, long courseID) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isPresent()) {
            Student updatedStudent = student.get();
            Set<Course> courseList = updatedStudent.getCourses();
            Optional<Course> optionalCourse = courseServices.getCourse(courseID);
            if (optionalCourse.isPresent()) {
                courseList.add(optionalCourse.get());
                updatedStudent.setCourses(courseList);
                studentRepository.save(updatedStudent);
            }
        }
    }

    public List<Student> getStudentsByAge(Integer age) {
        System.out.println(age);
        return studentRepository.getStudentsByAge(age);
    }

    public List<StudentDto> getAllFullName() {
        return
                studentRepository.findAll().stream()
                        .map(student -> modelMapper.map(student, StudentDto.class))
                        .collect(Collectors.toList());
    }

    public void deleteCourse(long studentID, long courseId) {

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

    public Student getStudentBuId(long id) {
        return studentRepository.findById(id).orElseThrow();
    }
}
