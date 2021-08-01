package com.example.demo.services;

import com.example.demo.courses.CourseDto;
import com.example.demo.entities.Course;
import com.example.demo.entities.Teacher;
import com.example.demo.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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

    public List<CourseDto> getCourses() {
        return courseRepository.getCoursesWithStudents();
    }

    public void deleteCourse(long ID) {
        Optional<Course> courseOptional = courseRepository.findById(ID);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            System.out.println(course.getTeacherList().size());
            course.getTeacherList().clear();
            System.out.println(course.getTeacherList().size());
        }
        studentServices.removeCourseFromAll(ID);
        courseRepository.deleteById(ID);
    }

    public void addTeacherForCourse(long courseID, Teacher teacher) {
        Optional<Course> courseOptional = courseRepository.findById(courseID);
        Course course = courseOptional.get();
        System.out.println(courseID);
        System.out.println(teacher.toString());
        System.out.println(course.getTeacherList().size());
        course.getTeacherList().add(teacher);
        System.out.println(course.getTeacherList().size());
    }

}
