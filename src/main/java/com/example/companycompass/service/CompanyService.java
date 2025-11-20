package com.example.companycompass.service;

import com.example.companycompass.model.Company;
import com.example.companycompass.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    // Create
    public void addCompany(Company company) {
        companyRepository.save(company);
    }

    // Read
    public Company getCompanyById(Long companyId) {
        return companyRepository
                .findById(companyId)
                .orElseThrow(() -> new EmptyResultDataAccessException("Company not found with id: ", Math.toIntExact(companyId)));
    }

    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    // Update
    public void updateCompany(Company company) {
        Company existingCompany =
                companyRepository
                        .findById(company.getId())
                        .orElseThrow(
                                () -> new EmptyResultDataAccessException("Company not found with id: ", Math.toIntExact(company.getId())));
        existingCompany.setName(company.getName());
        existingCompany.setLocation(company.getLocation());
        companyRepository.save(existingCompany);
    }

    // Delete
    public void deleteCompany(Long companyId) {
        if (!companyRepository.existsById(companyId)) {
            throw new EmptyResultDataAccessException("Company not found with id: ", Math.toIntExact(companyId));
        }
        companyRepository.deleteById(companyId);
    }
}
