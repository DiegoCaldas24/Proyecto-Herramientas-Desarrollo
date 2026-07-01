package com.ERPMuni.ERPmuni.controller;

import com.ERPMuni.ERPmuni.model.UserModel;
import com.ERPMuni.ERPmuni.security.UserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reports")
public class ReportsController {
    @GetMapping()
    public String reports(Model model, @AuthenticationPrincipal UserDetails userDetails) {

        UserModel userLoged = userDetails.getUserModel();
        model.addAttribute("userRol", userLoged.getRol());
        return "reports";
    }
}
