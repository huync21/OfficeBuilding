package com.example.officebuilding.repository;

import com.example.officebuilding.entities.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICompanyRepository extends JpaRepository<CompanyEntity, Integer> {
}