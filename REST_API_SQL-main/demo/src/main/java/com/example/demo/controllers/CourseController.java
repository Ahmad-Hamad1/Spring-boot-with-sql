package com.example.demo.controllers;

import com.example.demo.dtos.CourseDto;
import com.example.demo.entities.Course;
import com.example.demo.services.CourseServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private CourseServices courseServices;

    @Autowired
    public void setCourseServices(CourseServices courseServices) {
        this.courseServices = courseServices;
    }

    @GetMapping("/getCourses")
    public List<CourseDto> getCourses() {
        return courseServices.getCourses();
    }

    @PostMapping("/insertOne")
    public CompletableFuture<Course> insertCourse(@RequestBody CourseDto courseDto) {
        return courseServices.addCourse(courseDto);
    }

    @PostMapping("/addTeacherForCourse")
    public void addTeacherForCourse(long courseId, long teacherId) {
        courseServices.addTeacherForCourse(courseId, teacherId);
    }

    @DeleteMapping("/deleteCourse/{ID}")
    public void deleteCourse(@PathVariable("ID") Long id) {
        courseServices.deleteCourse(id);
    }

}
