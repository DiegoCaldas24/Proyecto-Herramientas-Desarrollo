package com.ERPMuni.ERPmuni.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "attendances",
        uniqueConstraints = @UniqueConstraint(name = "uk_attendance_employee_date", columnNames = {"id_employee", "attendance_date"}))
public class AttendanceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAttendance;

    @ManyToOne
    @JoinColumn(name = "id_employee")
    private EmployeesModel employees;

    @Column(name = "attendance_date")
    private LocalDate attendanceDate;

    @Column(name = "entry_date")
    private LocalDateTime entryDate;

    @Column(name = "departure_date")
    private LocalDateTime departureDate;

    @Column(name = "status_exit")
    private String statusExit;

    @Column(name = "status")
    private String status;

    @PrePersist
    protected void onCreate() {
        if (attendanceDate == null) {
            attendanceDate = LocalDate.now();
        }
    }

}
