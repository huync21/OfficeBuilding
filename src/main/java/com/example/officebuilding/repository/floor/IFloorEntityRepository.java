package com.example.officebuilding.repository.floor;


import com.example.officebuilding.entities.FloorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFloorEntityRepository extends JpaRepository<FloorEntity, Integer> {

}
