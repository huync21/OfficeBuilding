package com.example.officebuilding.service.company_employee;

import com.example.officebuilding.dtos.CompanyEmployeeDTO;
import com.example.officebuilding.service.IGeneralService;

import java.util.List;

public interface ICompanyEmployeeService extends IGeneralService<CompanyEmployeeDTO> {
    public List<CompanyEmployeeDTO> findAllEmployeeOfCompany(Integer id);
    public int countCompanyEmployeesByCompanyID(Integer companyId);
    List<CompanyEmployeeDTO> findEmployeesByNameAndCompanyId(String empName,Integer companyId);
}
