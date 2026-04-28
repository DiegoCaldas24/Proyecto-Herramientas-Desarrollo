package com.ERPMuni.ERPmuni.repository;

import com.ERPMuni.ERPmuni.model.EmployeesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EmployeesRepository extends JpaRepository<EmployeesModel, Integer> {
    List<EmployeesModel> findAll();

    List<EmployeesModel> findEmployeesModelByStatus(String status);

    Optional<EmployeesModel> findByDni(String dniEmployee);

    Optional<EmployeesModel> findByIdEmployee(int idEmployee);

    <S extends EmployeesModel> S save(S employee);

    @Query("SELECT COUNT(e.idEmployee) FROM EmployeesModel e WHERE e.status = 'ACTIVO'")
    int countAllEmployees();
}
