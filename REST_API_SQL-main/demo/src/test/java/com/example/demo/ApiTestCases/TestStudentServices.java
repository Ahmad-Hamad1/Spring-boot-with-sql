package com.example.demo.ApiTestCases;

import com.example.demo.DOTOS.StudentDto;
import com.example.demo.entities.Student;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.services.StudentServices;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TestStudentServices {

    @Autowired
    ModelMapper mapper;

    @MockBean
    private StudentRepository studentRepository;
    @Autowired
    private StudentServices studentServices;

    @Test
    public void testGetStudentById() throws ExecutionException, InterruptedException {
        long id = 1;
        Student student = StudentSample.getStudent();
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        assertThat(studentServices.getStudentById(id).get().getId()).isEqualTo(id);
    }

    @Test
    public void testUpdateStudent() throws ExecutionException, InterruptedException {
        long id = 1;
        Student student = StudentSample.getStudent();
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        assertThat(studentServices.
                updateStudent(id, mapper.map(student, StudentDto.class)).get()).isInstanceOf(StudentDto.class);
    }
    @Test
    public void studentDeleteTest() throws ExecutionException, InterruptedException {
        long id = 1;
        Student student = StudentSample.getStudent();
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).delete(student);
        assertNull(studentServices.deleteStudent(id).get());

    }

}
