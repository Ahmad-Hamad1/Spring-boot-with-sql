package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long Id;

    @Column(name = "TEXT", nullable = false)
    private String name;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "ToughtBy",
            joinColumns = @JoinColumn(name = "Course_ID"),
            inverseJoinColumns = @JoinColumn(name = "TEACHER_ID")
    )
    private Set<Teacher> teacherList = new HashSet<>();


    @ManyToMany(mappedBy = "courses", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private Set<Student> studentList = new HashSet<>();

    public Course() {
    }

    public Course(String name, Set<Teacher> teacherList, Set<Student> studentList) {
        this.name = name;
        this.teacherList = teacherList;
        this.studentList = studentList;
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

    public Set<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(Set<Student> studentList) {
        this.studentList = studentList;
    }

    public long getId() {
        return Id;
    }

    @Override
    public String toString() {
        return "Course{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", teacherList=" + teacherList +
                ", studentList=" + studentList +
                '}';
    }
}
