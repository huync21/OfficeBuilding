package com.example.officebuilding.service.building_employee;

import com.example.officebuilding.dtos.BuildingEmployeeDTO;
import com.example.officebuilding.dtos.SalaryDTO;
import com.example.officebuilding.service.IGeneralService;

public interface IBuildingEmployeeService extends IGeneralService<BuildingEmployeeDTO> {
    void createNewBuildingEmployeeBySalaryId(Integer salaryId, BuildingEmployeeDTO buildingEmployeeDTO);
}
