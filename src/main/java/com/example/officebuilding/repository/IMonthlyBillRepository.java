package com.example.officebuilding.repository;

import com.example.officebuilding.entities.MonthlyBillEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMonthlyBillRepository extends JpaRepository<MonthlyBillEntity, Integer> {
    List<MonthlyBillEntity> findMonthlyBillEntitiesByContract_IdAndAndMonth_Id(Integer contractId,Integer monthId);
}