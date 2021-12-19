package com.example.officebuilding.repository;

import com.example.officebuilding.entities.BuildingEmployeeEntity;
import com.example.officebuilding.entities.WorkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IWorkRepository extends JpaRepository<WorkEntity, Integer> {
    List<WorkEntity> findWorkEntitiesByTitleContaining(String title);
    List<WorkEntity> findWorkEntitiesByBuildingEmployee_Id(Integer id);
    List <WorkEntity> findWorkEntitiesByTitleContainingAndBuildingEmployee_Id(String title, Integer id);
}
