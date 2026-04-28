package com.ERPMuni.ERPmuni.service;

import com.ERPMuni.ERPmuni.model.EmployeesModel;
import com.ERPMuni.ERPmuni.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeesService {

    private final EmployeesRepository employeesRepository;

    @Autowired
    public EmployeesService(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    public List<EmployeesModel> getAllEmployess() {
        return employeesRepository.findAll();
    }

    public List<EmployeesModel> filterEmployees(String status) {
        return employeesRepository.findEmployeesModelByStatus(status);
    }

    public void saveEmployee(EmployeesModel employeesModel) {
        employeesRepository.save(employeesModel);
    }

    public Optional<EmployeesModel> getEmployee(int idEmployee) {
        return employeesRepository.findByIdEmployee(idEmployee);
    }

    public int countEmployees() {
        return employeesRepository.countAllEmployees();
    }
}
