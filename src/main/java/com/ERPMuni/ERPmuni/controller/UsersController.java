package com.ERPMuni.ERPmuni.controller;

import com.ERPMuni.ERPmuni.model.EmployeesModel;
import com.ERPMuni.ERPmuni.model.UserModel;
import com.ERPMuni.ERPmuni.security.UserDetails;
import com.ERPMuni.ERPmuni.service.EmployeesService;
import com.ERPMuni.ERPmuni.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;
    private final EmployeesService employeesService;
    private final PasswordEncoder passwordEncoder;

    public UsersController(UserService userService, EmployeesService employeesService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.employeesService = employeesService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String users(Model model, @AuthenticationPrincipal UserDetails user) {
        UserModel userLoged = user.getUserModel();

        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", new UserModel());
        model.addAttribute("employees", employeesService.getAllEmployess());

        model.addAttribute("userName", userLoged.getEmployee() != null ? userLoged.getEmployee().getNames() : userLoged.getEmail());
        model.addAttribute("userRol", userLoged.getRol());

        return "users";
    }

    @PostMapping("/save")
    public String saveUser(
            @RequestParam("idUser") String idUserParam,
            @RequestParam("email") String email,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam("rol") String rol,
            @RequestParam(value = "estatus", defaultValue = "true") boolean estatus,
            @RequestParam(value = "idEmployee", required = false) Integer idEmployee,
            RedirectAttributes redirectAttributes) {
        try {
            UserModel user;
            boolean isNew = idUserParam == null || idUserParam.isEmpty();

            if (isNew) {
                user = UserModel.builder()
                        .email(email)
                        .password(passwordEncoder.encode(password))
                        .rol(rol)
                        .fecha_ingreso(String.valueOf(LocalDateTime.now()))
                        .estatus(estatus)
                        .build();
            } else {
                user = userService.findById(Integer.parseInt(idUserParam)).orElse(null);
                if (user == null) {
                    redirectAttributes.addFlashAttribute("message", "Usuario no encontrado");
                    return "redirect:/users";
                }
                if (password != null && !password.isEmpty()) {
                    user.setPassword(passwordEncoder.encode(password));
                }
                user.setEmail(email);
                user.setRol(rol);
                user.setEstatus(estatus);
            }

            if (idEmployee != null) {
                employeesService.getEmployee(idEmployee).ifPresent(user::setEmployee);
            } else {
                user.setEmployee(null);
            }

            userService.save(user);
            redirectAttributes.addFlashAttribute("message", "Se realizo correctamente la accion");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error en la accion");
        }
        return "redirect:/users";
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public UserModel getUser(@PathVariable int id) {
        return userService.findById(id).orElse(null);
    }
}
