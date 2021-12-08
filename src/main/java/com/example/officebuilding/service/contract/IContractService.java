package com.example.officebuilding.service.contract;

import com.example.officebuilding.dtos.ContractDTO;
import com.example.officebuilding.service.IGeneralService;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IContractService extends IGeneralService<ContractDTO> {
     double getSumOfRentedArea(Integer companyId);
     double getSumOfRentedAreaFloor(Integer floorId);
     void createContract(Integer companyId, Integer floorId, ContractDTO contractDTO );
     List<ContractDTO> getContractsByFloorId(Integer floorId);
     List<ContractDTO> getContractsByCompanyId(Integer companyId);
}
