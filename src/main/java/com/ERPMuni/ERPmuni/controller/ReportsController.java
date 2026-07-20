package com.ERPMuni.ERPmuni.controller;

import com.ERPMuni.ERPmuni.model.UserModel;
import com.ERPMuni.ERPmuni.security.UserDetails;
import com.ERPMuni.ERPmuni.service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/reports")
public class ReportsController {

    private final ReportsService reportsService;

    @Autowired
    public ReportsController(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    @GetMapping()
    public String reports(Model model, @AuthenticationPrincipal UserDetails userDetails) {

        UserModel userLoged = userDetails.getUserModel();
        model.addAttribute("userRol", userLoged.getRol());
        return "reports";
    }

    @GetMapping("/data")
    @ResponseBody
    public Map<String, Object> reportsData() {
        return reportsService.getReportsData();
    }
}
