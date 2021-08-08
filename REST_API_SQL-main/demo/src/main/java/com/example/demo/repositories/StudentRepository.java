package com.example.demo.repositories;

import com.example.demo.entities.Student;
import com.example.demo.student.StudentProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query(
            "SELECT st FROM Students st  WHERE st.age >= ?1 ORDER BY st.age"
    )
    List<Student> getStudentsByAge(Integer age);

    @Query(
            "SELECT ST FROM Students ST"
    )
    List<StudentProjection> getStudentsFullName();

    Optional<Student> findByFirstName(String firstName);

    Student getStudentByEmail(String email);
}
