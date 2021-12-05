package com.example.officebuilding.service.salary;

import com.example.officebuilding.dtos.SalaryDTO;
import com.example.officebuilding.service.IGeneralService;

import java.util.List;

public interface ISalaryService extends IGeneralService<SalaryDTO> {
    List<SalaryDTO> findSalariesByServiceId(Integer serviceId);

    void createNewSalaryByServiceId(Integer serviceId,SalaryDTO salaryDTO);
}
