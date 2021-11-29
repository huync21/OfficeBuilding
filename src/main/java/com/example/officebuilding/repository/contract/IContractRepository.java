package com.example.officebuilding.repository.contract;

import com.example.officebuilding.entities.ContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IContractRepository extends JpaRepository<ContractEntity, Integer> {
}