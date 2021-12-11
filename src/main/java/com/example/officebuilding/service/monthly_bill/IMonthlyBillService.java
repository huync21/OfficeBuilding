package com.example.officebuilding.service.monthly_bill;

import com.example.officebuilding.dtos.MonthlyBillDTO;


import java.util.List;

public interface IMonthlyBillService {
    List<MonthlyBillDTO> findMonthlyBillsOfCompanyInAMonth(Integer companyId, Integer monthId);
}
