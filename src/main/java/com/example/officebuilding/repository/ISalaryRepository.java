package com.example.officebuilding.repository;

import com.example.officebuilding.entities.SalaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISalaryRepository extends JpaRepository<SalaryEntity, Integer> {
}