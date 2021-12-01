package com.example.officebuilding.service.service_contract;

import com.example.officebuilding.dtos.ServiceContractDTO;
import com.example.officebuilding.service.IGeneralService;

import java.util.List;

public interface IServiceContractService extends IGeneralService<ServiceContractDTO> {
    public List<ServiceContractDTO> findAllServiceContractOfCompany(Integer companyId);
}
