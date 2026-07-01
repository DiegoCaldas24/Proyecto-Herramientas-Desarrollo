package com.ERPMuni.ERPmuni.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorHandler implements ErrorController {

    @RequestMapping("/error")
    public String error(HttpServletRequest request) {
        return "Error";
    }
}
