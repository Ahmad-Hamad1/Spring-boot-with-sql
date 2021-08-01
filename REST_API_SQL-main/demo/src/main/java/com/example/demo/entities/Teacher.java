package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long Id;
    @Column(name = "TEXT", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "teacherList", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private Set<Course> courses = new HashSet<>();

    public Teacher() {
    }

    public Teacher(String name, Set<Course> courses) {
        this.name = name;
        this.courses = courses;
    }

    public Teacher(long id, String name, Set<Course> courses) {
        Id = id;
        this.name = name;
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", courses=" + courses +
                '}';
    }
}
