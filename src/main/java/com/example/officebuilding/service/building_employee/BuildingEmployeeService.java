package com.example.officebuilding.service.building_employee;

import com.example.officebuilding.dtos.BuildingEmployeeDTO;
import com.example.officebuilding.dtos.BuildingEmployeeDTO;
import com.example.officebuilding.entities.BuildingEmployeeEntity;
import com.example.officebuilding.repository.IBuildingEmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BuildingEmployeeService implements IBuildingEmployeeService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IBuildingEmployeeRepository buildingEmployeeRepository;

    @Override
    public List<BuildingEmployeeDTO> findAll() {
        // Gọi repo lấy từ db
        List<BuildingEmployeeEntity> buildingEmployeeEntities = buildingEmployeeRepository.findAll();

        //Chuyển các entities thành các đối tượng Data Transfer Object(DTO) rồi trả về cho controller
        List<BuildingEmployeeDTO> buildingEmployeeDTOS = buildingEmployeeEntities.stream().map(buildingEmployeeEntity -> modelMapper.map(buildingEmployeeEntity, BuildingEmployeeDTO.class))
                .collect(Collectors.toList());
        return buildingEmployeeDTOS;
    }

    @Override
    public Optional<BuildingEmployeeDTO> findById(Integer id) {
        // Gọi repo lấy dữ liệu entity từ db
        Optional<BuildingEmployeeEntity> buildingEmployeeEntity = buildingEmployeeRepository.findById(id);

        //Chuyển entity thành DTO rồi trả về cho controller:
        Optional<BuildingEmployeeDTO> buildingEmployeeDTOOptional = buildingEmployeeEntity.map(buildingEmployeeEntity1 -> modelMapper.map(buildingEmployeeEntity1, BuildingEmployeeDTO.class));
        return buildingEmployeeDTOOptional;
    }

    @Override
    public BuildingEmployeeDTO save(BuildingEmployeeDTO buildingEmployeeDTO) {
        // Chuyển DTO thành entity
        BuildingEmployeeEntity buildingEmployeeEntity = modelMapper.map(buildingEmployeeDTO, BuildingEmployeeEntity.class);

        // Lưu xuống db và trả về đối tượng entity đã được cập nhật
        BuildingEmployeeEntity updatedBuildingEmployeeEntity = buildingEmployeeRepository.save(buildingEmployeeEntity);

        // Chuyển lại đối tượng entity đã được cập nhật sang DTO để trả về:
        return modelMapper.map(updatedBuildingEmployeeEntity,BuildingEmployeeDTO.class);
    }


    @Override
    public void remove(Integer id) {
        buildingEmployeeRepository.deleteById(id);
    }
}