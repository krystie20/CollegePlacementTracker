package com.campusplacement.backend.controller;

import com.campusplacement.backend.model.Interview;
import com.campusplacement.backend.repository.InterviewRepository;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/interviews")
@CrossOrigin(origins = "http://localhost:5173")
public class InterviewController {
    private final InterviewRepository interviewRepository;
    public InterviewController(InterviewRepository ir) { this.interviewRepository = ir; }

    @PostMapping
    public Map<String, Object> schedule(@RequestBody Interview interview) {
        Map<String, Object> response = new HashMap<>();
        Interview created = interviewRepository.createInterview(interview);
        response.put("success", created != null);
        response.put("interview", created);
        return response;
    }

    @GetMapping("/student/{studentId}")
    public List<Interview> getByStudent(@PathVariable int studentId) { return interviewRepository.findByStudentId(studentId); }
}