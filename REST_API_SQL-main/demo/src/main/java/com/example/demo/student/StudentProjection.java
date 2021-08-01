package com.example.demo.student;

import com.example.demo.courses.CourseProjection;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface StudentProjection {
    @Value("#{target.firstName + ' ' + target.lastName}")
    String getFullName();

    List<CourseProjection> getCourses();
}
