package com.example.demo.services;

import com.example.demo.entities.Course;
import com.example.demo.entities.Teacher;
import com.example.demo.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TeacherServices {
    private TeacherRepository teacherRepository;
    private CourseServices courseServices;

    @Autowired

    public void setTeacherRepository(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Autowired
    public void setCourseServices(CourseServices courseServices) {
        this.courseServices = courseServices;
    }

    public void insertTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }
    public Optional<Teacher> getTeacherById(long teacherId) {
        return teacherRepository.findById(teacherId);
    }

    public void deleteTeacher(long ID) {
        teacherRepository.deleteById(ID);
    }


}

