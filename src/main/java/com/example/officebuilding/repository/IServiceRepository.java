package com.example.officebuilding.repository;

import com.example.officebuilding.entities.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IServiceRepository extends JpaRepository<ServiceEntity, Integer> {
    // Tìm những dịch vụ bắt buộc
     List<ServiceEntity> findServiceEntitiesByRequired(Integer isRequired);
     List<ServiceEntity> findServiceEntitiesByNameContaining(String name);
}