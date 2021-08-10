package com.example.demo.ApiTestCases;

import com.example.demo.entities.Student;
import com.example.demo.exceptions.ApiError;
import com.example.demo.repositories.StudentRepository;
import com.example.demo.services.StudentServices;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
     class TestingStudentRepository {
    private StudentRepository studentRepository;
    @Autowired
    public void setStudentServices(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Test
    @Order(1)
    void testInsertStudent() throws ExecutionException, InterruptedException {
        Student student = new Student();
        student.setFirstName("Test");
        student.setLastName("Student");
        student.setAge(30);
        student.setEmail("test@gmail.com");
        Student addedStudent = studentRepository.save(student);
        assertNotNull(studentRepository.findById(addedStudent.getID()));
    }

    @Test
    @Order(2)
    void testUpdateStudent() throws ExecutionException, InterruptedException {
        Student student = studentRepository.getStudentByEmail("test@gmail.com");
        student.setLastName("Updated Student");
        studentRepository.save(student);
        Student updatedStudent = studentRepository.getStudentByEmail("test@gmail.com");
        assertNotNull(updatedStudent);
        assertEquals("Updated Student", updatedStudent.getLastName());
    }

    @Test
    @Order(3)
    void testDeleteStudent() throws ExecutionException, InterruptedException {
        long id;
        Student student = studentRepository.getStudentByEmail("test@gmail.com");
        assertNotNull(student);
        id = student.getID();
        studentRepository.deleteById(id);
        assertNull(studentRepository.getStudentByEmail("test@gmail.com"));
    }
}