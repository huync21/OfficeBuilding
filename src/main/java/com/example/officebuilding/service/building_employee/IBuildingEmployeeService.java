package com.example.officebuilding.service.building_employee;

import com.example.officebuilding.dtos.BuildingEmployeeDTO;
import com.example.officebuilding.dtos.CompanyDTO;
import com.example.officebuilding.dtos.SalaryDTO;
import com.example.officebuilding.service.IGeneralService;

import java.util.List;

public interface IBuildingEmployeeService extends IGeneralService<BuildingEmployeeDTO> {
    void createNewBuildingEmployeeBySalaryId(Integer salaryId, BuildingEmployeeDTO buildingEmployeeDTO);
    void updateBuildingEmployee(Integer empId, Integer salaryId, BuildingEmployeeDTO buildingEmployeeDTO);
    List<BuildingEmployeeDTO> findBuildingEmployeeByName(String name);
}
