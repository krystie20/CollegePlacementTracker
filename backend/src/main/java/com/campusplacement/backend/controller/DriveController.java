package com.campusplacement.backend.controller;

import com.campusplacement.backend.model.Drive;
import com.campusplacement.backend.repository.DriveRepository;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/drives")
@CrossOrigin(origins = "http://localhost:5173")
public class DriveController {

    private final DriveRepository driveRepository;
    public DriveController(DriveRepository driveRepository) { this.driveRepository = driveRepository; }

    @GetMapping
    public List<Drive> getAllDrives() { return driveRepository.findAll(); }

    @GetMapping("/company/{companyId}")
    public List<Drive> getDrivesByCompany(@PathVariable int companyId) { return driveRepository.findByCompanyId(companyId); }

    @GetMapping("/{id}")
    public Map<String, Object> getDrive(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        Drive drive = driveRepository.findById(id);
        if (drive == null) { response.put("success", false); response.put("message", "Drive not found."); return response; }
        response.put("success", true);
        response.put("drive", drive);
        return response;
    }

    @PostMapping
    public Map<String, Object> createDrive(@RequestBody Drive drive) {
        Map<String, Object> response = new HashMap<>();
        Drive created = driveRepository.createDrive(drive);
        if (created == null) { response.put("success", false); response.put("message", "Failed to create drive."); return response; }
        response.put("success", true);
        response.put("message", "Drive created successfully.");
        response.put("drive", created);
        return response;
    }

    @PutMapping("/{id}")
    public Map<String, Object> updateDrive(@PathVariable int id, @RequestBody Drive drive) {
        Map<String, Object> response = new HashMap<>();
        boolean updated = driveRepository.updateDrive(id, drive);
        response.put("success", updated);
        response.put("message", updated ? "Drive updated successfully." : "Update failed.");
        return response;
    }
}