package com.example.demo.controllers;

import com.example.demo.courses.CourseDto;
import com.example.demo.entities.Course;
import com.example.demo.services.CourseServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Course insertCourse(@RequestBody Course course) {
        return courseServices.addCourse(course);
    }

    @DeleteMapping("/deleteCourse/{ID}")
    public void deleteCourse(@PathVariable("ID") Long ID) {
        courseServices.deleteCourse(ID);
    }

}
