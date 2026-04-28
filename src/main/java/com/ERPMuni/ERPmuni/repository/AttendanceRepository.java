package com.ERPMuni.ERPmuni.repository;

import com.ERPMuni.ERPmuni.model.AttendanceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceModel, Integer> {
    @Override
    <S extends AttendanceModel> S save(S employee);

    Optional<AttendanceModel> findByEmployees_IdEmployee(int idEmployee);

    Optional<AttendanceModel> findByEmployees_Dni(String dniEmployee);

    Optional<AttendanceModel> findByEmployees_IdEmployeeAndEntryDate(int idEmployee, LocalDateTime entryDate);

    List<AttendanceModel> findAll();

    @Query("SELECT COUNT(a.status) FROM AttendanceModel a WHERE a.status = 'TEMPRANO'")
    int countStatusWell();

    @Query("SELECT COUNT(a.status) FROM AttendanceModel a WHERE a.status = 'TARDE'")
    int countStatusMid();

    @Query("SELECT COUNT(a.status) FROM AttendanceModel a WHERE a.status = 'FALTA'")
    int countStatusBad();
}
