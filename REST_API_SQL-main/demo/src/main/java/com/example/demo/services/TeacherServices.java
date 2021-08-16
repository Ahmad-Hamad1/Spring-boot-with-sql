package com.example.demo.services;

import com.example.demo.dtos.TeacherDto;
import com.example.demo.entities.Teacher;
import com.example.demo.repositories.TeacherRepository;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ModelMapper mapper;

    @Async
    public void insertTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    @Async
    public CompletableFuture<TeacherDto> getTeacherById(long teacherId) {
        Optional<Teacher> teacherOptional = teacherRepository.findById(teacherId);
        if (teacherOptional.isEmpty())
            throw new NoSuchElementException("No teacher with this ID");
        return CompletableFuture.completedFuture(mapper.map(teacherOptional.get(), TeacherDto.class));
    }

    @Async
    public void deleteTeacher(long id) {
        teacherRepository.deleteById(id);
    }


}

