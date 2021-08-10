package com.example.demo.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "TEXT", nullable = false)
    private String name;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(
            name = "ToughtBy",
            joinColumns = @JoinColumn(name = "Course_ID"),
            inverseJoinColumns = @JoinColumn(name = "TEACHER_ID")
    )
    private Set<Teacher> teacherList = new HashSet<>();


    public Course() {
    }

    public Course(String name, Set<Teacher> teacherList) {
        this.name = name;
        this.teacherList = teacherList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Teacher> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(Set<Teacher> teacherList) {
        this.teacherList = teacherList;
    }


    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Course{" +
                "Id=" + id +
                ", name='" + name + '\'' +
                ", teacherList=" + teacherList +
                '}';
    }
}
