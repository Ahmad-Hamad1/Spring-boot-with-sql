package com.example.demo.controllers;

import com.example.demo.dtos.StudentDto;
import com.example.demo.entities.Student;
import com.example.demo.services.StudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/students")
public class StudentController {

    private StudentServices studentServices;

    @Autowired
    public void setStudentServices(StudentServices studentServices) {
        this.studentServices = studentServices;
    }

    @GetMapping("/getAll")
    public List<StudentDto> getStudents() {
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
    public List<StudentDto> getStudentsByAge(@PathVariable("age") Integer age) {
        return studentServices.getStudentsByAge(age);
    }

    @GetMapping("/getById/{ID}")
    public CompletableFuture<StudentDto> getStudentById(@PathVariable("ID") long id) throws ExecutionException, InterruptedException {
        return studentServices.getStudentById(id);
    }

    @GetMapping("/getByEmail/{email}")
    public CompletableFuture<StudentDto> getStudentByEmail(@PathVariable("email") String email) {
        return studentServices.getStudentByEmail(email);
    }

    @PostMapping("/insertNew")
    public CompletableFuture<StudentDto> insertStudent(@RequestBody StudentDto studentDto) {
        return studentServices.insertStudent(studentDto);
    }

    @PostMapping("/addCourse/{studentID}/{courseID}")
    public void addCourse(@PathVariable("studentID") long studentId,
                          @PathVariable("courseID") long courseID) throws ExecutionException, InterruptedException {
        studentServices.addCourse(studentId, courseID);
    }

    @PutMapping("/update/{id}")
    public CompletableFuture<StudentDto> updateStudent(@PathVariable("id") Long id, @RequestBody StudentDto studentDto) {
        return studentServices.updateStudent(id, studentDto);
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

}