package com.example.demo.ApiTestCases;

import com.example.demo.entities.Student;
import com.example.demo.services.StudentServices;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentApiTesting {
    private StudentServices studentServices;
    @Autowired
    public void setStudentServices(StudentServices studentServices) {
        this.studentServices = studentServices;
    }

    @Test
    @Order(1)
    public void testInsertStudent() throws ExecutionException, InterruptedException {
        Student student = new Student();
        student.setFirstName("Test");
        student.setLastName("Student");
        student.setAge(30);
        student.setEmail("test@gmail.com");
        CompletableFuture<Student> addedStudent = studentServices.insertStudent(student);
        assertNotNull(studentServices.getStudentById(addedStudent.get().getID()));
    }

    @Test
    @Order(2)
    public void testUpdateStudent() throws ExecutionException, InterruptedException {
        CompletableFuture<Student> studentCompletableFuture = studentServices.getStudentByEmail("test@gmail.com",false);
        Student student = studentCompletableFuture.get();
        student.setLastName("Updated Student");
        CompletableFuture<Student> studentCompletableFuture1 = studentServices.updateStudent(student.getID(), student);
        studentCompletableFuture1.get();
        CompletableFuture<Student> updatedStudent = studentServices.getStudentByEmail("test@gmail.com",false);
        System.out.println(updatedStudent.get().getLastName());
        assertEquals("Updated Student", updatedStudent.get().getLastName());
    }

    @Test
    @Order(3)
    public void testDeleteStudent() throws ExecutionException, InterruptedException {
        long id;
        CompletableFuture<Student> studentCompletableFuture = studentServices.getStudentByEmail("test@gmail.com",false);
        Student student = studentCompletableFuture.get();
        id = student.getID();
        studentServices.deleteStudent(id).get();
        Object presentCheck = studentServices.getStudentByEmail("test@gmail.com",true).get();
        assertNull(presentCheck);
    }
}