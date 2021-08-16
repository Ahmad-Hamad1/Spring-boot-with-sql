package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDto {

    private long id;

    private String name;

    @Override
    public String toString() {
        return "Teacher{" +
                "Id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}