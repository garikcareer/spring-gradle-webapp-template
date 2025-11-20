package com.example.companycompass.controller;

import com.example.companycompass.model.Company;
import com.example.companycompass.service.CompanyService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company")
public class CompanyRestController {
    private final CompanyService companyService;

    public CompanyRestController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // 1. GET Company for Edit Modal
    @GetMapping("/get")
    public ResponseEntity<Company> getCompany(@RequestParam Long companyId) {
        Company company = companyService.getCompanyById(companyId);
        return ResponseEntity.ok(company);
    }

    // 2. DELETE Company
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        // Return a simple success message
        return ResponseEntity.ok(Map.of("message", "Deleted successfully", "id", id));
    }

    // 3. UPDATE Company
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable Long id, @RequestBody Company companyDetails) {
        // Ensure the ID matches
        companyDetails.setId(id);
        companyService.updateCompany(companyDetails);
        return ResponseEntity.ok(Map.of("message", "Updated successfully", "id", id));
    }
}