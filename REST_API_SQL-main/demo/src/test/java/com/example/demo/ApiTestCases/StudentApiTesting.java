package com.example.demo.ApiTestCases;

import com.example.demo.entities.Student;
import com.example.demo.services.StudentServices;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

// test
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
    public void testInsertStudent() {
        Student student = new Student();
        student.setFirstName("Test");
        student.setLastName("Student");
        student.setAge(30);
        student.setEmail("test@gmail.com");
        Student addedStudent = studentServices.insertStudent(student);
        assertNotNull(studentServices.getStudentById(addedStudent.getID()));
    }

    @Test
    @Order(2)
    public void testUpdateStudent() {
        Student student = studentServices.getStudentByEmail("test@gmail.com");
        student.setLastName("Updated Student");
        studentServices.updateStudent(student.getID(), student);
        Student updatedStudent = studentServices.getStudentByEmail("test@gmail.com");
        assertEquals("Updated Student", updatedStudent.getLastName());
    }

    @Test
    @Order(3)
    public void testDeleteStudent() {
        long id;
        Student student = studentServices.getStudentByEmail("test@gmail.com");
        id = student.getID();
        studentServices.deleteStudent(id);
        assertNull(studentServices.getStudentByEmail("test@gmail.com"));
    }
}
