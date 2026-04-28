package com.ERPMuni.ERPmuni.controller;

import com.ERPMuni.ERPmuni.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final EmployeesService employeesService;

    @Autowired
    public DashboardController(EmployeesService employeesService) {
        this.employeesService = employeesService;
    }

    @GetMapping()
    public String home(Model model) {
        model.addAttribute("count", employeesService.countEmployees());
        return "dashboard";
    }
}
