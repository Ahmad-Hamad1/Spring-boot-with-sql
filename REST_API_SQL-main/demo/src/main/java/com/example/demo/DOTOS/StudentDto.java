package com.example.demo.DOTOS;

import com.example.demo.entities.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

    private long id;
    private int age;
    private String firstName;
    private String lastName;
    private String email;
    private Set<Course> courses = new HashSet<>();

    @Override
    public String toString() {
        return "Student{" +
                "ID=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", courses=" + courses +
                '}';
    }
}
