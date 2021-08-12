package com.example.demo.ApiTestCases;

import com.example.demo.entities.Student;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.services.StudentServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TestStudentServices {

    private StudentServices studentServices;

    @Autowired
    public void setStudentServices(StudentServices studentServices) {
        this.studentServices = studentServices;
    }

    @MockBean
    private StudentRepository studentRepository;

    @Test
    public void testGetStudentById() throws ExecutionException, InterruptedException {
        long id  = 1;
        Student student = StudentSample.getStudent();
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        assertThat(studentServices.getStudentById(id).get()).isEqualTo(student);
    }
    @Test
    public void testUpdateStudent() throws ExecutionException, InterruptedException {
        long id  = 1;
        Student student = StudentSample.getStudent();
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        when(studentRepository.save(student)).thenReturn(student);
        assertThat(studentServices.updateStudent(id,student).get()).isEqualTo(student);
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
