package com.campusplacement.backend.controller;

import com.campusplacement.backend.model.*;
import com.campusplacement.backend.repository.*;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/placements")
@CrossOrigin(origins = "http://localhost:5173")
public class PlacementController {
    private final PlacementRepository placementRepository;
    private final ApplicationRepository applicationRepository;
    private final DriveRepository driveRepository;

    public PlacementController(PlacementRepository pr, ApplicationRepository ar, DriveRepository dr) {
        this.placementRepository = pr; this.applicationRepository = ar; this.driveRepository = dr;
    }

    @GetMapping
    public List<Placement> getAll() { return placementRepository.findAll(); }

    @GetMapping("/student/{studentId}")
    public Map<String, Object> getByStudent(@PathVariable int studentId) {
        Map<String, Object> response = new HashMap<>();
        Placement p = placementRepository.findByStudentId(studentId);
        response.put("success", p != null);
        response.put("placement", p);
        return response;
    }

    @PostMapping
    public Map<String, Object> select(@RequestBody Map<String, Integer> body) {
        Map<String, Object> response = new HashMap<>();
        int applicationId = body.get("applicationId");
        Application app = applicationRepository.findById(applicationId);
        if (app == null) { response.put("success", false); response.put("message", "Application not found."); return response; }
        Drive drive = driveRepository.findById(app.getDriveId());
        applicationRepository.updateStatus(applicationId, "SELECTED");

        Placement placement = new Placement();
        placement.setStudentId(app.getStudentId());
        placement.setCompanyId(drive.getCompanyId());
        placement.setDriveId(drive.getId());
        placement.setRole(drive.getRole());
        placement.setPackageLpa(drive.getPackageLpa());

        Placement created = placementRepository.createPlacement(placement);
        response.put("success", created != null);
        response.put("placement", created);
        return response;
    }
}