package com.example.officebuilding.repository;

import com.example.officebuilding.entities.ServiceContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IServiceContractRepository extends JpaRepository<ServiceContractEntity, Integer> {
    @Query(value = "select * from service_contract where company_id=:id", nativeQuery = true)
    List<ServiceContractEntity> findAllServiceContractOfCompany(@Param("id") Integer id);
}