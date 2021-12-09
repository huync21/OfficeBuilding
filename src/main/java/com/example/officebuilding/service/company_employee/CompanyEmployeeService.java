package com.example.officebuilding.service.company_employee;

import com.example.officebuilding.dtos.CompanyEmployeeDTO;
import com.example.officebuilding.entities.CompanyEmployeeEntity;
import com.example.officebuilding.repository.ICompanyEmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyEmployeeService implements ICompanyEmployeeService{
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ICompanyEmployeeRepository companyEmployeeRepository;

    @Override
    public List<CompanyEmployeeDTO> findAll() {
        // Lấy list entities từ db lên
        List<CompanyEmployeeEntity> companyEmployeeEntities = companyEmployeeRepository.findAll();

        // Đổi list entities đó về dto rồi trả về
        List<CompanyEmployeeDTO> companyEmployeeDTOs = companyEmployeeEntities.stream()
                .map(companyEmployeeEntity -> modelMapper.map(companyEmployeeEntity, CompanyEmployeeDTO.class))
                .collect(Collectors.toList());
        return companyEmployeeDTOs;
    }

    @Override
    public Optional<CompanyEmployeeDTO> findById(Integer id) {
        // Lấy entity từ db lên
        Optional<CompanyEmployeeEntity> companyEmployeeEntity = companyEmployeeRepository.findById(id);

        // Chuyển sang dto rồi trả về
        Optional<CompanyEmployeeDTO> companyEmployeeDTO = companyEmployeeEntity
                .map(companyEmployeeEntity1 -> modelMapper.map(companyEmployeeEntity1, CompanyEmployeeDTO.class));
        return companyEmployeeDTO;
    }


    @Override
    public CompanyEmployeeDTO save(CompanyEmployeeDTO companyEmployeeDTO) {
        // chuyển từ DTO sang entity:
        CompanyEmployeeEntity companyEmployeeEntity = modelMapper.map(companyEmployeeDTO, CompanyEmployeeEntity.class);

        // save xuống db:
        CompanyEmployeeEntity updatedEntity = companyEmployeeRepository.save(companyEmployeeEntity);

        // chuyển lại đối tượng đã được update sang dto và trả về:
        return modelMapper.map(updatedEntity,CompanyEmployeeDTO.class);
    }

    @Override
    public void remove(Integer id) {
        companyEmployeeRepository.deleteById(id);
    }

    @Override
    public List<CompanyEmployeeDTO> findAllEmployeeOfCompany(Integer id) {
        // Lấy list entities từ db lên
        List<CompanyEmployeeEntity> companyEmployeeEntities = companyEmployeeRepository.getCompanyEmployeeEntitiesByCompany_Id(id);

        // Đổi list entities đó về dto rồi trả về
        List<CompanyEmployeeDTO> companyEmployeeDTOs = companyEmployeeEntities.stream()
                .map(companyEmployeeEntity -> modelMapper.map(companyEmployeeEntity, CompanyEmployeeDTO.class))
                .collect(Collectors.toList());
        return companyEmployeeDTOs;
    }

    @Override
    public int countCompanyEmployeesByCompanyID(Integer companyId) {
        return companyEmployeeRepository.countCompanyEmployeeEntitiesByCompany_Id(companyId);
    }

    @Override
    public List<CompanyEmployeeDTO> findEmployeesByNameAndCompanyId(String empName, Integer companyId) {
        return companyEmployeeRepository.getCompanyEmployeeEntitiesByNameAndCompany_Id(empName,companyId)
                .stream().map(companyEmployee -> modelMapper.map(companyEmployee,CompanyEmployeeDTO.class))
                .collect(Collectors.toList());
    }
}
