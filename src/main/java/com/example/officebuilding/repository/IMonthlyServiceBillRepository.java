package com.example.officebuilding.repository;

import com.example.officebuilding.entities.MonthlyServiceBillEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMonthlyServiceBillRepository extends JpaRepository<MonthlyServiceBillEntity, Integer> {

}