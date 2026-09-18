package com.campusplacement.backend.controller;

import com.campusplacement.backend.model.RoundResult;
import com.campusplacement.backend.repository.RoundResultRepository;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class RoundResultController {
    private final RoundResultRepository resultRepository;
    public RoundResultController(RoundResultRepository rr) { this.resultRepository = rr; }

    @PostMapping("/round-results")
    public Map<String, Object> saveResult(@RequestBody RoundResult result) {
        Map<String, Object> response = new HashMap<>();
        RoundResult saved = resultRepository.createOrUpdate(result);
        response.put("success", saved != null);
        response.put("result", saved);
        return response;
    }

    @GetMapping("/applications/{id}/results")
    public List<RoundResult> getResults(@PathVariable int id) { return resultRepository.findByApplicationId(id); }
}