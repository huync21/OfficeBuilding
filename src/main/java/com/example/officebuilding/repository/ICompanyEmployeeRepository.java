package com.example.officebuilding.repository;

import com.example.officebuilding.entities.CompanyEmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICompanyEmployeeRepository extends JpaRepository<CompanyEmployeeEntity, Integer> {
    List<CompanyEmployeeEntity> getCompanyEmployeeEntitiesByCompany_Id(@Param("id") Integer id);
    int countCompanyEmployeeEntitiesByCompany_Id(Integer companyId);
    List<CompanyEmployeeEntity> getCompanyEmployeeEntitiesByNameAndCompany_Id(String empName,Integer companyId);
}
