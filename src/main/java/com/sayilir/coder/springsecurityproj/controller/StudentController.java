package com.sayilir.coder.springsecurityproj.controller;

import com.sayilir.coder.springsecurityproj.model.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class StudentController {
    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "eyyup"),
            new Student(2, "semra"),
            new Student(3, "zehra")
    );

    @GetMapping("/students/{studentid}")
    public Student getStudent(@PathVariable("studentid") Integer studentID) {
        return STUDENTS.stream()
                .filter(student -> studentID.equals(student.getStudentId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("stundent" + studentID + " not exist!!!!"));
    }
}
