package com.example.officebuilding.repository;

import com.example.officebuilding.entities.ServiceContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IServiceContractRepository extends JpaRepository<ServiceContractEntity, Integer> {
    List<ServiceContractEntity> getServiceContractEntitiesByCompany_Id(@Param("id") Integer id);
}