package com.example.demo.repositories;

import com.example.demo.courses.CourseDto;
import com.example.demo.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query(
            "SELECT new com.example.demo.courses.CourseDto ( c.name , st.firstName ) FROM Course c JOIN c.studentList st"
    )
    List<CourseDto> getCoursesWithStudents();
}
