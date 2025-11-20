package com.example.companycompass.controller;

import com.example.companycompass.model.Company;
import com.example.companycompass.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CompanyPageController {

    private final CompanyService companyService;

    @Autowired
    public CompanyPageController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // 1. LIST COMPANIES
    @GetMapping("/")
    public ModelAndView getAllCompanies() {
        ModelAndView mav = new ModelAndView("layout");
        mav.addObject("content", "company");
        mav.addObject("pageTitle", "Companies");
        mav.addObject("companyList", companyService.getCompanies());
        return mav;
    }

    // 2. SHOW ADD FORM
    @GetMapping("/add")
    public ModelAndView addCompany() {
        ModelAndView mav = new ModelAndView("layout");
        mav.addObject("content", "addcompany");
        mav.addObject("pageTitle", "Add Company");
        mav.addObject("company", new Company());
        return mav;
    }

    // 3. SHOW EDIT FORM
    // FIX: Added ("id") to @PathVariable so Spring knows which URL part to grab
    @GetMapping("/edit/{id}")
    public ModelAndView editCompany(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("layout");
        mav.addObject("content", "addcompany");
        mav.addObject("pageTitle", "Edit Company");
        mav.addObject("company", companyService.getCompanyById(id));
        return mav;
    }

    // 4. SAVE (Handles both Add and Edit)
    @PostMapping("/save")
    public String saveCompany(@ModelAttribute Company company) {
        if (company.getId() == null) {
            companyService.addCompany(company);
        } else {
            companyService.updateCompany(company);
        }
        return "redirect:/";
    }

    // 5. DELETE
    // FIX: Added ("id") to @PathVariable
    @GetMapping("/delete/{id}")
    public String deleteCompany(@PathVariable("id") Long id) {
        companyService.deleteCompany(id);
        return "redirect:/";
    }

    // Navigation Helpers
    @GetMapping("/about")
    public ModelAndView about() {
        ModelAndView mav = new ModelAndView("layout");
        mav.addObject("content", "about");
        mav.addObject("pageTitle", "About");
        return mav;
    }

    @GetMapping("/contact")
    public ModelAndView contact() {
        ModelAndView mav = new ModelAndView("layout");
        mav.addObject("content", "contact");
        mav.addObject("pageTitle", "Contact");
        return mav;
    }
}