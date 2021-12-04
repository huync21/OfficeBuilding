package com.example.officebuilding.repository;

import com.example.officebuilding.entities.SalaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ISalaryRepository extends JpaRepository<SalaryEntity, Integer> {
    List<SalaryEntity> findSalaryEntitiesByService_Id(Integer serviceId);
}