package com.campusplacement.backend.controller;

import com.campusplacement.backend.model.Student;
import com.campusplacement.backend.repository.StudentRepository;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:5173")
public class StudentController {

    private final StudentRepository studentRepository;
    public StudentController(StudentRepository studentRepository) { this.studentRepository = studentRepository; }

    @GetMapping
    public List<Student> getAllStudents() { return studentRepository.findAll(); }

    @GetMapping("/{id}")
    public Map<String, Object> getStudent(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        Student student = studentRepository.findById(id);
        if (student == null) { response.put("success", false); response.put("message", "Student not found."); return response; }
        response.put("success", true);
        response.put("student", student);
        return response;
    }

    @GetMapping("/by-user/{userId}")
    public Map<String, Object> getStudentByUserId(@PathVariable int userId) {
        Map<String, Object> response = new HashMap<>();
        Student student = studentRepository.findByUserId(userId);
        if (student == null) { response.put("success", false); response.put("message", "Student not found."); return response; }
        response.put("success", true);
        response.put("student", student);
        return response;
    }

    @PutMapping("/{id}")
    public Map<String, Object> updateStudent(@PathVariable int id, @RequestBody Student student) {
        Map<String, Object> response = new HashMap<>();
        boolean updated = studentRepository.updateStudent(id, student);
        response.put("success", updated);
        response.put("message", updated ? "Student profile updated." : "Update failed.");
        return response;
    }
}