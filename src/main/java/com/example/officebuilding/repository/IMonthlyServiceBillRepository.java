package com.example.officebuilding.repository;

import com.example.officebuilding.entities.MonthlyServiceBillEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMonthlyServiceBillRepository extends JpaRepository<MonthlyServiceBillEntity, Integer> {
     List<MonthlyServiceBillEntity> findMonthlyServiceBillEntitiesByMonth_IdAndServiceContract_Id(Integer monthId,Integer serviceContractId);
}