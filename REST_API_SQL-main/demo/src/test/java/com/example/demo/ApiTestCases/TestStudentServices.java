package com.example.demo.ApiTestCases;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.demo.entities.Student;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.services.StudentServices;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestStudentServices {
    private StudentServices studentServices;

    @Autowired
    public void setStudentServices(StudentServices studentServices) {
        this.studentServices = studentServices;
    }

    @Mock
    private StudentRepository studentRepository;

    @BeforeTestMethod
    public void beforeEachTest(){
        studentRepository = mock(StudentRepository.class);
    }

    @Test
    @Order(1)
    public void testGetStudentById() throws ExecutionException, InterruptedException {
        Student student = new Student();
        student.setFirstName("aaaa");
        student.setLastName("bbbbb");
        student.setAge(21);
        student.setEmail("aaaa@gmail.com");
        student.setID(1);
        when(studentRepository.findById((long)1)).thenReturn(Optional.of(student));
        assertThat(studentRepository.findById((long)1).get()).isEqualTo(student);
    }
}
