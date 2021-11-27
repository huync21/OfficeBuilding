package com.example.officebuilding.service.company_employee;

import com.example.officebuilding.entities.CompanyEmployeeEntity;
import com.example.officebuilding.repository.floor.ICompanyEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyEmployeeService implements ICompanyEmployeeService{
    @Autowired
    private ICompanyEmployeeRepository companyEmployeeRepo;
    @Override
    public Iterable<CompanyEmployeeEntity> findAll() {
        return this.companyEmployeeRepo.findAll();
    }

    @Override
    public Optional<CompanyEmployeeEntity> findById(Integer id) {
        return this.companyEmployeeRepo.findById(id);
    }

    @Override
    public CompanyEmployeeEntity save(CompanyEmployeeEntity companyEmployeeEntity) {
        return this.companyEmployeeRepo.save(companyEmployeeEntity);
    }

    @Override
    public void remove(Integer id) {
        this.companyEmployeeRepo.deleteById(id);
    }
}
