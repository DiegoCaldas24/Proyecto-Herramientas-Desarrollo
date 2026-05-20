package com.ERPMuni.ERPmuni.security;

import com.ERPMuni.ERPmuni.model.EmployeesModel;
import com.ERPMuni.ERPmuni.model.UserModel;
import com.ERPMuni.ERPmuni.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AdminInitializer implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "admin@admin.com";

        UserModel userAdmin = userRepository.findByEmail(adminEmail).orElse(null);
        if (userAdmin == null) {
            UserModel admin = UserModel.builder()
                    .email(adminEmail)
                    .employee(EmployeesModel.builder().idEmployee(1).build())
                    .password(passwordEncoder.encode("12345"))
                    .rol("ADMINISTRADOR")
                    .fecha_ingreso(String.valueOf(LocalDateTime.now()))
                    .estatus(true)
                    .build();
            userRepository.save(admin);
            System.out.println(" Usuario ADMINISTRADOR creado con éxito.");
        } else {
            System.out.println("️ Usuario ADMINISTRADOR ya existe.");
        }

    }
}
