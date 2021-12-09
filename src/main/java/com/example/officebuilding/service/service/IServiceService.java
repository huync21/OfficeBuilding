package com.example.officebuilding.service.service;


import com.example.officebuilding.dtos.ServiceDTO;
import com.example.officebuilding.service.IGeneralService;

import java.util.List;

public interface IServiceService extends IGeneralService<ServiceDTO> {
     List<ServiceDTO> findAllUnregisterdServices(Integer companyId);
     List<ServiceDTO> findAllUnregisterdServicesByServiceName(Integer companyId, String serviceName);
     List<ServiceDTO> findServicesByName(String name);
}
