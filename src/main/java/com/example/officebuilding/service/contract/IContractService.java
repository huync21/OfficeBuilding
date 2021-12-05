package com.example.officebuilding.service.contract;

import com.example.officebuilding.dtos.ContractDTO;
import com.example.officebuilding.service.IGeneralService;
import org.springframework.data.jpa.repository.Query;

public interface IContractService extends IGeneralService<ContractDTO> {
     double getSumOfRentedArea(Integer companyId);
}
