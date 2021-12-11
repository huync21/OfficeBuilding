package com.example.officebuilding.service.monthly_service_bill;

import com.example.officebuilding.dtos.MonthlyServiceBillDTO;
import com.example.officebuilding.dtos.ServiceContractDTO;
import com.example.officebuilding.service.IGeneralService;

import java.util.Date;
import java.util.List;


public interface IMonthlyServiceBillService extends IGeneralService<MonthlyServiceBillDTO> {
    double calculateMoney(Date startDate, Date endDate, ServiceContractDTO serviceContractDTO);
    List<MonthlyServiceBillDTO> findMonthlyServiceBillsOfCompanyInAMonth(Integer companyId,Integer monthId);
}
