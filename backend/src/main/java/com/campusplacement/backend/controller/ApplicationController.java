package com.campusplacement.backend.controller;

import com.campusplacement.backend.model.*;
import com.campusplacement.backend.repository.*;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "http://localhost:5173")
public class ApplicationController {
    private final ApplicationRepository applicationRepository;
    private final StudentRepository studentRepository;
    private final DriveRepository driveRepository;

    public ApplicationController(ApplicationRepository ar, StudentRepository sr, DriveRepository dr) {
        this.applicationRepository = ar; this.studentRepository = sr; this.driveRepository = dr;
    }

    @GetMapping
    public List<Application> getAll() { return applicationRepository.findAll(); }

    @GetMapping("/student/{studentId}")
    public List<Application> getByStudent(@PathVariable int studentId) { return applicationRepository.findByStudentId(studentId); }

    @GetMapping("/drive/{driveId}")
    public List<Application> getByDrive(@PathVariable int driveId) { return applicationRepository.findByDriveId(driveId); }

    @PostMapping
    public Map<String, Object> apply(@RequestBody Map<String, Integer> body) {
        Map<String, Object> response = new HashMap<>();
        int studentId = body.get("studentId");
        int driveId = body.get("driveId");

        Student student = studentRepository.findById(studentId);
        Drive drive = driveRepository.findById(driveId);

        if (student == null || drive == null) {
            response.put("success", false); response.put("message", "Student or drive not found."); return response;
        }
        if (applicationRepository.exists(studentId, driveId)) {
            response.put("success", false); response.put("message", "You have already applied to this drive."); return response;
        }

        List<String> reasons = new ArrayList<>();
        if (student.getCgpa().compareTo(drive.getMinCgpa()) < 0) reasons.add("Minimum CGPA required: " + drive.getMinCgpa());
        if (student.getBacklogs() > drive.getMaxBacklogs()) reasons.add("Maximum backlogs allowed: " + drive.getMaxBacklogs());
        if (!drive.getAllowedDepartments().toUpperCase().contains(student.getDepartment().toUpperCase())) reasons.add("Department not eligible: " + drive.getAllowedDepartments() + " only");
        if (student.getBatch() != drive.getPassingYear()) reasons.add("Batch " + drive.getPassingYear() + " only");

        if (!reasons.isEmpty()) {
            response.put("success", false); response.put("eligible", false);
            response.put("message", "Not eligible to apply."); response.put("reasons", reasons);
            return response;
        }

        Application app = applicationRepository.createApplication(studentId, driveId);
        response.put("success", true); response.put("eligible", true);
        response.put("message", "Application submitted successfully."); response.put("application", app);
        return response;
    }

    @PutMapping("/{id}/status")
    public Map<String, Object> updateStatus(@PathVariable int id, @RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", applicationRepository.updateStatus(id, body.get("status")));
        return response;
    }
}