package com.ERPMuni.ERPmuni.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "id_user")
    private int idUser;

    @ManyToOne
    @JoinColumn(name = "id_employee")
    private EmployeesModel employee;

    private String email;

    private String password;

    private String rol;

    private String fecha_ingreso;

    private boolean estatus;
}
