package com.example.officebuilding.service.company;

import com.example.officebuilding.dtos.CompanyDTO;
import com.example.officebuilding.dtos.MonthDTO;
import com.example.officebuilding.dtos.MonthlyFeeOfCompanyDTO;
import com.example.officebuilding.service.IGeneralService;

import java.util.List;

public interface ICompanyService extends IGeneralService<CompanyDTO> {
    List<MonthlyFeeOfCompanyDTO> getMonthlyFeeOfCompany(Integer monthId);
    CompanyDTO update(CompanyDTO companyDTO);
}
