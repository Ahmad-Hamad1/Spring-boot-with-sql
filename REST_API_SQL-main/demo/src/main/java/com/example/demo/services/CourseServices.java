package com.example.demo.services;

import com.example.demo.entities.Course;
import com.example.demo.entities.Teacher;
import com.example.demo.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class CourseServices {
    private CourseRepository courseRepository;
    private TeacherServices teacherServices;
    private StudentServices studentServices;

    @Autowired
    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Autowired
    public void setTeacherServices(TeacherServices teacherServices) {
        this.teacherServices = teacherServices;
    }

    @Autowired
    public void setStudentServices(StudentServices studentServices) {
        this.studentServices = studentServices;
    }

    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    public Optional<Course> getCourse(long ID) {
        return courseRepository.findById(ID);
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public void deleteCourse(long ID) {
        Optional<Course> courseOptional = courseRepository.findById(ID);
        if (courseOptional.isPresent()) {
            studentServices.removeCourseFromAll(ID);
            courseRepository.deleteById(ID);
        } else
            throw new NoSuchElementException("There is no course with this Id");
    }

    public void addTeacherForCourse(long courseID, long teacherId) {
        Optional<Course> courseOptional = courseRepository.findById(courseID);
        Optional<Teacher> teacherOptional = teacherServices.getTeacherById(teacherId);
        if (courseOptional.isEmpty())
            throw new NoSuchElementException("No course with this Id");
        if (teacherOptional.isEmpty())
            throw new NoSuchElementException("No Teacher with this Id");
        courseOptional.get().getTeacherList().add(teacherOptional.get());
    }

}
