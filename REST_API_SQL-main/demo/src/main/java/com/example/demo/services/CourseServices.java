package com.example.demo.services;

import com.example.demo.entities.Course;
import com.example.demo.entities.Teacher;
import com.example.demo.repositories.CourseRepository;
import com.example.demo.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class CourseServices {
    private CourseRepository courseRepository;
    private TeacherRepository teacherRepository;
    private StudentServices studentServices;

    @Autowired
    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Autowired
    public void setTeacherRepository(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Autowired
    public void setStudentServices(StudentServices studentServices) {
        this.studentServices = studentServices;
    }

    @Async
    public CompletableFuture<Course> addCourse(Course course) {
        return CompletableFuture.completedFuture(courseRepository.save(course));
    }

    @Async
    public CompletableFuture<Course> getCourse(long id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isEmpty())
            throw new NoSuchElementException("No course with this ID");
        return CompletableFuture.completedFuture(courseOptional.get());
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    @Async
    public void deleteCourse(long id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            studentServices.removeCourseFromAll(id);
            courseRepository.deleteById(id);
        } else
            throw new NoSuchElementException("There is no course with this Id");
    }

    @Async
    public void addTeacherForCourse(long courseID, long teacherId) {
        Optional<Course> courseOptional = courseRepository.findById(courseID);
        Optional<Teacher> teacherOptional = teacherRepository.findById(teacherId);
        if (courseOptional.isEmpty())
            throw new NoSuchElementException("No course with this Id");
        if (teacherOptional.isEmpty())
            throw new NoSuchElementException("No Teacher with this Id");
        courseOptional.get().getTeacherList().add(teacherOptional.get());
    }

}
