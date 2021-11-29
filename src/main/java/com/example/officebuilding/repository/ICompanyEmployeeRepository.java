package com.example.officebuilding.repository;

import com.example.officebuilding.entities.CompanyEmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICompanyEmployeeRepository extends JpaRepository<CompanyEmployeeEntity, Integer> {
}
