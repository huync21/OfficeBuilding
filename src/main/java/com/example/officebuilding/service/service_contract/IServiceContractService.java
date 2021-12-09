package com.example.officebuilding.service.service_contract;

import com.example.officebuilding.dtos.ServiceContractDTO;
import com.example.officebuilding.service.IGeneralService;
import java.util.List;
import java.util.Optional;

public interface IServiceContractService extends IGeneralService<ServiceContractDTO> {
    List<ServiceContractDTO> findAllServiceContractOfCompany(Integer companyId);
    Optional<ServiceContractDTO> createServiceContract(Integer companyId, Integer serviceId, ServiceContractDTO serviceContractDTO);
    List<ServiceContractDTO> findServiceContractByServiceName(String serviceName);
}
