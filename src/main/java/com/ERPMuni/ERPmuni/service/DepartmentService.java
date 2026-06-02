package com.ERPMuni.ERPmuni.service;

import com.ERPMuni.ERPmuni.model.DepartmentModel;
import com.ERPMuni.ERPmuni.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<DepartmentModel> getAllDepartments(){
        return departmentRepository.findAll();
    }
}
