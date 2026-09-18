package com.campusplacement.backend.controller;

import com.campusplacement.backend.model.Round;
import com.campusplacement.backend.repository.RoundRepository;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/drives/{driveId}/rounds")
@CrossOrigin(origins = "http://localhost:5173")
public class RoundController {
    private final RoundRepository roundRepository;
    public RoundController(RoundRepository roundRepository) { this.roundRepository = roundRepository; }

    @GetMapping
    public List<Round> getRounds(@PathVariable int driveId) { return roundRepository.findByDriveId(driveId); }

    @PostMapping
    public Map<String, Object> createRound(@PathVariable int driveId, @RequestBody Round round) {
        round.setDriveId(driveId);
        Map<String, Object> response = new HashMap<>();
        Round created = roundRepository.createRound(round);
        response.put("success", created != null);
        response.put("round", created);
        return response;
    }

    @DeleteMapping("/{roundId}")
    public Map<String, Object> deleteRound(@PathVariable int driveId, @PathVariable int roundId) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", roundRepository.deleteRound(roundId));
        return response;
    }
}