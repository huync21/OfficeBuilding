package com.example.officebuilding.repository;

import com.example.officebuilding.entities.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IServiceRepository extends JpaRepository<ServiceEntity, Integer> {
}