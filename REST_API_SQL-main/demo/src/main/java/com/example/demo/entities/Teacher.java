package com.example.demo.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(name = "TEXT", nullable = false)
    private String name;

    public Teacher() {
    }

    public Teacher(String name) {
        this.name = name;
    }

    public Teacher(long id, String name) {
        id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "Id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
