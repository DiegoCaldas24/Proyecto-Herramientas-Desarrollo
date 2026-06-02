package com.ERPMuni.ERPmuni.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employees")
public class EmployeesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_employee")
    private Integer idEmployee;

    @ManyToOne
    @JoinColumn(name = "id_department")
    private DepartmentModel department;

    @Column(name = "names")
    private String names;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "dni")
    private String dni;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
     private String phone;

    @Column(name = "date_hire")
    private String date_hire;

    @Column(name = "salary")
    private double salary;

    @Column(name = "position")
    private String position;

    @Column(name = "status")
    private String status = "ACTIVO";

    @Column(name = "registration_date")
    private String registration_date;

    @PrePersist
    protected void onCreate() {
        date_hire = LocalDate.now().toString();
        registration_date = LocalDate.now().toString();
    }
}
