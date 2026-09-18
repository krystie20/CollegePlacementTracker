package com.campusplacement.backend.controller;

import com.campusplacement.backend.model.Company;
import com.campusplacement.backend.repository.CompanyRepository;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin(origins = "http://localhost:5173")
public class CompanyController {

    private final CompanyRepository companyRepository;
    public CompanyController(CompanyRepository companyRepository) { this.companyRepository = companyRepository; }

    @GetMapping
    public List<Company> getAllCompanies() { return companyRepository.findAll(); }

    @GetMapping("/{id}")
    public Map<String, Object> getCompany(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        Company company = companyRepository.findById(id);
        if (company == null) { response.put("success", false); response.put("message", "Company not found."); return response; }
        response.put("success", true);
        response.put("company", company);
        return response;
    }

    @GetMapping("/by-user/{userId}")
    public Map<String, Object> getCompanyByUserId(@PathVariable int userId) {
        Map<String, Object> response = new HashMap<>();
        Company company = companyRepository.findByUserId(userId);
        if (company == null) { response.put("success", false); response.put("message", "Company not found."); return response; }
        response.put("success", true);
        response.put("company", company);
        return response;
    }

    @PutMapping("/{id}")
    public Map<String, Object> updateCompany(@PathVariable int id, @RequestBody Company company) {
        Map<String, Object> response = new HashMap<>();
        boolean updated = companyRepository.updateCompany(id, company);
        response.put("success", updated);
        response.put("message", updated ? "Company profile updated." : "Update failed.");
        return response;
    }
}