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
    public CompletableFuture<Student> getStudentById(@PathVariable("ID") long id) throws ExecutionException, InterruptedException {
        return studentServices.getStudentById(id);
    }
    @GetMapping("/getByEmail/{email}")
    public CompletableFuture<Student> getStudentByEmail(@PathVariable("email") String email){
        return studentServices.getStudentByEmail(email);
    }

    @PostMapping("/insertNew")
    public CompletableFuture<Student> insertStudent(@RequestBody Student student) {
        return studentServices.insertStudent(student);
    }

    @PostMapping("/addCourse/{studentID}/{courseID}")
    public void addCourse(@PathVariable("studentID") long studentId,
                          @PathVariable("courseID") long courseID) throws ExecutionException, InterruptedException {
        studentServices.addCourse(studentId, courseID);
    }

    @PutMapping("/update/{id}")
    public CompletableFuture<Student> updateStudent(@PathVariable("id") Long id, @RequestBody Student student) {
        return studentServices.updateStudent(id, student);
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