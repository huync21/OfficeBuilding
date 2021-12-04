package com.example.officebuilding.service.service;


import com.example.officebuilding.dtos.ServiceDTO;
import com.example.officebuilding.service.IGeneralService;

import java.util.List;

public interface IServiceService extends IGeneralService<ServiceDTO> {
    public List<ServiceDTO> findAllUnregisterdServices(Integer companyId);
}
