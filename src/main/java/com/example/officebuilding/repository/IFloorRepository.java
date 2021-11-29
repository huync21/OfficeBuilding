package com.example.officebuilding.repository;


import com.example.officebuilding.entities.FloorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFloorRepository extends JpaRepository<FloorEntity, Integer> {
}
