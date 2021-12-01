package com.example.officebuilding.repository;

import com.example.officebuilding.entities.CompanyEmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICompanyEmployeeRepository extends JpaRepository<CompanyEmployeeEntity, Integer> {
    @Query(value = "select * from company_employee where company_id=:id", nativeQuery = true)
    List<CompanyEmployeeEntity> findAllEmployeeOfCompany(@Param("id") Integer id);
}
