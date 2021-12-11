package com.example.officebuilding.repository;

import com.example.officebuilding.entities.MonthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMonthRepository extends JpaRepository<MonthEntity, Integer> {
}