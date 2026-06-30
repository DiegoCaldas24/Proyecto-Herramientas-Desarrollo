package com.ERPMuni.ERPmuni.security;

import com.ERPMuni.ERPmuni.model.DepartmentModel;
import com.ERPMuni.ERPmuni.model.UserModel;
import com.ERPMuni.ERPmuni.repository.DepartmentRepository;
import com.ERPMuni.ERPmuni.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
public class AdminInitializer implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "admin@admin.com";

        UserModel userAdmin = userRepository.findByEmail(adminEmail).orElse(null);
        if (userAdmin == null) {
            UserModel admin = UserModel.builder()
                    .email(adminEmail)
                    .employee(null)
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
        if (departmentRepository.findAll().isEmpty()) {

            ArrayList<DepartmentModel> departments = new ArrayList<>();
            departments.add(new  DepartmentModel().builder().name("desarrollo").code("D001").build());
            departments.add(new  DepartmentModel().builder().name("marketing").code("D002").build());
            departments.add(new  DepartmentModel().builder().name("ventas").code("D003").build());
            departments.add(new  DepartmentModel().builder().name("logistica").code("D004").build());
            departments.add(new  DepartmentModel().builder().name("administracion").code("D005").build());
            departments.add(new  DepartmentModel().builder().name("contabilidad").code("D006").build());
            for (DepartmentModel department: departments) {
                departmentRepository.save(department);
            }
        }
    }
}
