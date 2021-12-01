package com.example.officebuilding.repository;

import com.example.officebuilding.entities.BuildingEmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBuildingEmployeeRepository extends JpaRepository<BuildingEmployeeEntity, Integer> {
}