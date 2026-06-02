package com.ERPMuni.ERPmuni.repository;

import com.ERPMuni.ERPmuni.model.DepartmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentModel, Integer> {
    List<DepartmentModel> findAll();


}
