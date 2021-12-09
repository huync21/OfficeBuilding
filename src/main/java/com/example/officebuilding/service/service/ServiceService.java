package com.example.officebuilding.service.service;

import com.example.officebuilding.dao.ServiceDAO;
import com.example.officebuilding.dtos.ServiceDTO;
import com.example.officebuilding.entities.ServiceEntity;
import com.example.officebuilding.repository.IServiceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceService implements IServiceService{
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IServiceRepository serviceRepository;

    @Autowired
    private ServiceDAO serviceDAO;

    @Override
    public List<ServiceDTO> findAll() {
        // Gọi repo lấy từ db
        List<ServiceEntity> serviceEntities = serviceRepository.findAll();

        //Chuyển các entities thành các đối tượng Data Transfer Object(DTO) rồi trả về cho controller
        List<ServiceDTO> serviceDTOS = serviceEntities.stream().map(serviceEntity -> modelMapper.map(serviceEntity, ServiceDTO.class))
                .collect(Collectors.toList());
        return serviceDTOS;
    }

    @Override
    public Optional<ServiceDTO> findById(Integer id) {
        // Gọi repo lấy dữ liệu entity từ db
        Optional<ServiceEntity> serviceEntity = serviceRepository.findById(id);

        //Chuyển entity thành DTO rồi trả về cho controller:
        Optional<ServiceDTO> serviceDTOOptional = serviceEntity.map(serviceEntity1 -> modelMapper.map(serviceEntity1, ServiceDTO.class));
        return serviceDTOOptional;
    }

    @Override
    public ServiceDTO save(ServiceDTO serviceDTO) {
        // Chuyển DTO thành entity
        ServiceEntity serviceEntity = modelMapper.map(serviceDTO, ServiceEntity.class);

        // Lưu xuống db và trả về đối tượng entity đã được cập nhật
        ServiceEntity updatedServiceEntity = serviceRepository.save(serviceEntity);

        // Chuyển lại đối tượng entity đã được cập nhật sang DTO để trả về:
        return modelMapper.map(updatedServiceEntity,ServiceDTO.class);
    }


    @Override
    public void remove(Integer id) {
        serviceRepository.deleteById(id);
    }

    @Override
    public List<ServiceDTO> findAllUnregisterdServices(Integer companyId) {
        return serviceDAO.findAllUnregisterdServices(companyId);
    }

    @Override
    public List<ServiceDTO> findAllUnregisterdServicesByServiceName(Integer companyId, String serviceName) {
        return serviceDAO.findAllUnregisterdServices(companyId,serviceName);
    }

    @Override
    public List<ServiceDTO> findServicesByName(String name) {
        return serviceRepository.findServiceEntitiesByNameContaining(name)
                .stream().map(serviceEntity -> modelMapper.map(serviceEntity,ServiceDTO.class))
                .collect(Collectors.toList());
    }
}
