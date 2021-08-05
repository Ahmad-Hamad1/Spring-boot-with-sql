package com.example.demo.services;

import com.example.demo.entities.Teacher;
import com.example.demo.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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

    @Async
    public void insertTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    @Async
    public CompletableFuture<Teacher> getTeacherById(long teacherId) {
        Optional<Teacher> teacherOptional = teacherRepository.findById(teacherId);
        if (teacherOptional.isEmpty())
            throw new NoSuchElementException("No teacher with this ID");
        return CompletableFuture.completedFuture(teacherOptional.get());
    }

    @Async
    public void deleteTeacher(long ID) {
        teacherRepository.deleteById(ID);
    }


}

