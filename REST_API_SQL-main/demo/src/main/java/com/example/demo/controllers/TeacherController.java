package com.example.demo.controllers;

import com.example.demo.entities.Teacher;
import com.example.demo.services.TeacherServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private TeacherServices teacherServices;

    public TeacherServices getTeacherServices() {
        return teacherServices;
    }

    @Autowired
    public void setTeacherServices(TeacherServices teacherServices) {
        this.teacherServices = teacherServices;
    }

    @PostMapping("/add")
    public void insertTeacher(@RequestBody Teacher teacher) {
        teacherServices.insertTeacher(teacher);
    }

    @DeleteMapping("/deleteTeacher/{ID}")
    public void deleteCourse(@PathVariable("ID") Long ID) {
        teacherServices.deleteTeacher(ID);
    }
}
