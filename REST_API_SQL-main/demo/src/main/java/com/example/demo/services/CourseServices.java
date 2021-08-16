package com.example.demo.services;

import com.example.demo.dtos.CourseDto;
import com.example.demo.entities.Course;
import com.example.demo.entities.Teacher;
import com.example.demo.repositories.CourseRepository;
import com.example.demo.repositories.TeacherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CourseServices {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentServices studentServices;

    @Autowired
    private ModelMapper mapper;

    @Async
    public CompletableFuture<Course> addCourse(CourseDto courseDto) {
        return CompletableFuture.completedFuture(courseRepository.save(mapper.map(courseDto, Course.class)));
    }

    @Async
    public CompletableFuture<CourseDto> getCourse(long id) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isEmpty())
            throw new NoSuchElementException("No course with this ID");
        return CompletableFuture.completedFuture(mapper.map(courseOptional.get(), CourseDto.class));
    }

    public List<CourseDto> getCourses() {
        return courseRepository.findAll()
                .stream().map(course -> {
                    return mapper.map(course, CourseDto.class);
                })
                .collect(Collectors.toList());
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
