package com.example.demo.controllers;

import com.example.demo.entities.Student;
import com.example.demo.services.CourseServices;
import com.example.demo.services.StudentServices;
import com.example.demo.services.TeacherServices;
import com.example.demo.student.StudentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private StudentServices studentServices;
    private CourseServices courseServices;
    private TeacherServices teacherServices;

    @Autowired
    public void setStudentServices(StudentServices studentServices) {
        this.studentServices = studentServices;
    }

    @Autowired
    public void setCourseServices(CourseServices courseServices) {
        this.courseServices = courseServices;
    }

    @Autowired
    public void setTeacherServices(TeacherServices teacherServices) {
        this.teacherServices = teacherServices;
    }

    @GetMapping("/getAll")
    public List<Student> getStudents() {
        return studentServices.getStudents();
    }

    @GetMapping("/getAllFullName")
    public List<StudentDto> getAllFullName() {
        return studentServices.getAllFullName();
    }

    @GetMapping("/getAllPaged")
    public Page<Student> getStudentsPaged(@RequestBody PageDetails pageDetails) {
        return studentServices.getStudentsPaged(pageDetails.getPageNumber(), pageDetails.getPageSize()
                , pageDetails.getSortBy());
    }

    @GetMapping("/getByAge/{age}")
    public List<Student> getStudentsByAge(@PathVariable("age") Integer age) {
        return studentServices.getStudentsByAge(age);
    }

    @GetMapping("/getById/{ID}")
    public Student getStudentById(@PathVariable("ID") long ID) {
        return studentServices.getStudentById(ID);
    }

    @PostMapping("/insertNew")
    public Student insertStudent(@RequestBody Student student) {
        return studentServices.insertStudent(student);
    }

    @PostMapping("/addCourse/{studentID}/{courseID}")
    public void addCourse(@PathVariable("studentID") long studentId,
                          @PathVariable("courseID") long courseID) {
        studentServices.addCourse(studentId, courseID);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteStudent(@PathVariable("id") Long id) {
        studentServices.deleteStudent(id);
    }

    @DeleteMapping("/DeleteCourse/{studentID}/{courseId}")
    public void deleteCourse(@PathVariable("studentID") long studentID,
                             @PathVariable("courseId") long courseId) {
        studentServices.deleteCourseForStudent(studentID, courseId);
    }

    @PutMapping("/update/{id}")
    public Student updateStudent(@PathVariable("id") Long id, @RequestBody Student student) {
        return studentServices.updateStudent(id, student);
    }
}