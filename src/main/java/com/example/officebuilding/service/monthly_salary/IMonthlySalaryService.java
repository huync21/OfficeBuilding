package com.example.officebuilding.service.monthly_salary;

import com.example.officebuilding.dtos.MonthlySalaryDTO;
import com.example.officebuilding.service.IGeneralService;

import java.util.List;

public interface IMonthlySalaryService extends IGeneralService<MonthlySalaryDTO> {
    public List<MonthlySalaryDTO> getMonthlySalariesByMonthId(Integer monthId);
}
