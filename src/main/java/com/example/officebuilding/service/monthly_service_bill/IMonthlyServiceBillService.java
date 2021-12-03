package com.example.officebuilding.service.monthly_service_bill;

import com.example.officebuilding.dtos.MonthlyServiceBillDTO;
import com.example.officebuilding.dtos.ServiceContractDTO;
import com.example.officebuilding.service.IGeneralService;


public interface IMonthlyServiceBillService extends IGeneralService<MonthlyServiceBillDTO> {
    public double calculateMoney(String startDate, String endDate, ServiceContractDTO serviceContractDTO);
    public double calculateMoney( ServiceContractDTO serviceContractDTO);
}
