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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/registerAttendance")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final EmployeesService employeesService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService, EmployeesService employeesService) {
        this.attendanceService = attendanceService;
        this.employeesService = employeesService;
    }

    @GetMapping
    public String register(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            UserModel userLoged = userDetails.getUserModel();
            model.addAttribute("userRol", userLoged.getRol());
        }
        return "attendance";
    }

    @PostMapping("/attendance")
    public String saveEntryDate(
            @RequestParam("dni") String dniEmployee,
            RedirectAttributes redirectAttributes, UserDetails authenticatedPrincipal) {

        if (attendanceService.saveAttendance(dniEmployee)) {
            redirectAttributes.addFlashAttribute("message", "Se marco el registro correctamente");
        } else {
            redirectAttributes.addFlashAttribute("message", "Ocurrio un error al regsitrar");
        }
        return "redirect:/registerAttendance";
    }
}
