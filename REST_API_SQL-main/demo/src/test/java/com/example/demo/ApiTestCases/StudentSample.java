package com.example.demo.ApiTestCases;

import com.example.demo.entities.Student;

public class StudentSample{
    public static Student getStudent() {
        Student student = new Student();
        student.setFirstName("aaaa");
        student.setLastName("bbbbb");
        student.setAge(21);
        student.setEmail("aaaa@gmail.com");
        student.setID(1);
        return student;
    }
}
