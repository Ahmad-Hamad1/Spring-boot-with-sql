package com.example.demo.services;

import com.example.demo.entities.Course;
import com.example.demo.entities.Teacher;
import com.example.demo.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServices {
    private TeacherRepository teacherRepository;
    private CourseServices courseServices;

    @Autowired

    public void setTeacherRepository(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Autowired
    public void setCourseServices(CourseServices courseServices) {
        this.courseServices = courseServices;
    }

    public void insertTeacher(Teacher teacher) {
        for (Course course : teacher.getCourses()) {
            course.getTeacherList().add(teacher);
        }
        teacherRepository.save(teacher);
    }

    public void deleteTeacher(long ID) {
        teacherRepository.deleteById(ID);
    }

    public void removeCourseFromAll(long ID) {
        List<Teacher> teacherList = teacherRepository.findAll();
        for (Teacher teacher : teacherList) {
            for (Course course : teacher.getCourses()) {
                if (course.getId() == ID)
                    teacher.getCourses().remove(course);
            }
        }
    }
}
