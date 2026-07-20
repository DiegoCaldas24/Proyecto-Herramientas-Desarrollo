package com.ERPMuni.ERPmuni.controller;

import com.ERPMuni.ERPmuni.model.UserModel;
import com.ERPMuni.ERPmuni.security.UserDetails;
import com.ERPMuni.ERPmuni.service.AttendanceService;
import com.ERPMuni.ERPmuni.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final EmployeesService employeesService;
    private final AttendanceService attendanceService;

    @Autowired
    public DashboardController(EmployeesService employeesService,AttendanceService attendanceService) {
        this.employeesService = employeesService;
        this.attendanceService = attendanceService;
    }

    @GetMapping()
    public String home(Model model,@AuthenticationPrincipal UserDetails user) {
        UserModel userLoged = user.getUserModel();
        model.addAttribute("count", employeesService.countEmployees());
        model.addAttribute("assistance", attendanceService.findAllRegisters());
        model.addAttribute("attendanceWell", attendanceService.getCountAttendanceWellToday());
        model.addAttribute("attendanceMid", attendanceService.getCountAttendanceMidToday());
        model.addAttribute("attendanceBad", attendanceService.getCountAttendanceBadToday());
        model.addAttribute("userName", userLoged.getEmployee() != null ? userLoged.getEmployee().getNames() : userLoged.getEmail());
        model.addAttribute("userRol", userLoged.getRol());
        return "dashboard";
    }
}
