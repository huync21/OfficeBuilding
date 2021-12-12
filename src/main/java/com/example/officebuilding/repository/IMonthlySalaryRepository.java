package com.example.officebuilding.repository;

import com.example.officebuilding.entities.MonthlySalaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMonthlySalaryRepository extends JpaRepository<MonthlySalaryEntity,Integer> {
     List<MonthlySalaryEntity> findMonthlySalaryEntitiesByMonth_Id(Integer monthId);
}
