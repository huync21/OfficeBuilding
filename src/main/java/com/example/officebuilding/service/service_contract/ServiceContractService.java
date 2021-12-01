package com.example.officebuilding.service.service_contract;

import com.example.officebuilding.dtos.ServiceContractDTO;
import com.example.officebuilding.entities.ServiceContractEntity;
import com.example.officebuilding.repository.IServiceContractRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceContractService implements IServiceContractService{
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IServiceContractRepository serviceContractRepository;

    @Override
    public List<ServiceContractDTO> findAll() {
        // Gọi repo lấy từ db
        List<ServiceContractEntity> serviceContractEntities = serviceContractRepository.findAll();

        //Chuyển các entities thành các đối tượng Data Transfer Object(DTO) rồi trả về cho controller
        List<ServiceContractDTO> serviceContractDTOS = serviceContractEntities.stream().map(serviceContractEntity -> modelMapper.map(serviceContractEntity, ServiceContractDTO.class))
                .collect(Collectors.toList());
        return serviceContractDTOS;
    }

    @Override
    public Optional<ServiceContractDTO> findById(Integer id) {
        // Gọi repo lấy dữ liệu entity từ db
        Optional<ServiceContractEntity> serviceContractEntity = serviceContractRepository.findById(id);

        //Chuyển entity thành DTO rồi trả về cho controller:
        Optional<ServiceContractDTO> serviceContractDTOOptional = serviceContractEntity.map(serviceContractEntity1 -> modelMapper.map(serviceContractEntity1, ServiceContractDTO.class));
        return serviceContractDTOOptional;
    }

    @Override
    public ServiceContractDTO save(ServiceContractDTO serviceContractDTO) {
        // Chuyển DTO thành entity
        ServiceContractEntity serviceContractEntity = modelMapper.map(serviceContractDTO, ServiceContractEntity.class);

        // Lưu xuống db và trả về đối tượng entity đã được cập nhật
        ServiceContractEntity updatedServiceContractEntity = serviceContractRepository.save(serviceContractEntity);

        // Chuyển lại đối tượng entity đã được cập nhật sang DTO để trả về:
        return modelMapper.map(updatedServiceContractEntity,ServiceContractDTO.class);
    }


    @Override
    public void remove(Integer id) {
        serviceContractRepository.deleteById(id);
    }

    @Override
    public List<ServiceContractDTO> findAllServiceContractOfCompany(Integer companyId) {
        // Lấy list entities từ db lên
        List<ServiceContractEntity> serviceContractEntities = serviceContractRepository.getServiceContractEntitiesByCompany_Id(companyId);

        // Đổi list entities đó về dto rồi trả về
        List<ServiceContractDTO> serviceContractDTOs = serviceContractEntities.stream()
                .map(serviceContractEntity -> modelMapper.map(serviceContractEntity, ServiceContractDTO.class))
                .collect(Collectors.toList());
        return serviceContractDTOs;
    }
}
