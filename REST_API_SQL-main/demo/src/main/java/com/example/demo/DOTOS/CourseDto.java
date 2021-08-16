package com.example.demo.DOTOS;

import com.example.demo.entities.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {

    private long id;

    private String name;

    private Set<Teacher> teacherList = new HashSet<>();

    @Override
    public String toString() {
        return "Course{" +
                "Id=" + id +
                ", name='" + name + '\'' +
                ", teacherList=" + teacherList +
                '}';
    }
}
