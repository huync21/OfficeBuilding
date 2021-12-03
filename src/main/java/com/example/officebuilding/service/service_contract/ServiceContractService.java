package com.example.officebuilding.service.service_contract;

import com.example.officebuilding.dao.ServiceContractDAO;
import com.example.officebuilding.dtos.MessageDTO;
import com.example.officebuilding.dtos.ServiceContractDTO;
import com.example.officebuilding.entities.ServiceContractEntity;
import com.example.officebuilding.repository.IServiceContractRepository;
import com.example.officebuilding.service.monthly_service_bill.IMonthlyServiceBillService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceContractService implements IServiceContractService{
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IServiceContractRepository serviceContractRepository;

    @Autowired
    private IMonthlyServiceBillService monthlyServiceBillService;

    @Autowired
    private ServiceContractDAO serviceContractDAO;

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
        serviceContractEntities.forEach(serviceContract -> {

        });

        // Đổi list entities đó về dto
        List<ServiceContractDTO> serviceContractDTOs = serviceContractEntities.stream()
                .map(serviceContractEntity -> modelMapper.map(serviceContractEntity, ServiceContractDTO.class))
                .collect(Collectors.toList());

        serviceContractDTOs.forEach(serviceContractDTO -> {
            // Ràng buộc nâng cao của thầy: Lấy ra giá tiền tròn tháng hiện tại của các service contract đó với điều kiện diện tích mặt bằng và số người trong công ty
            serviceContractDTO.setCurrentPrice(monthlyServiceBillService.calculateMoney(serviceContractDTO));

            // Format lại ngày cho đẹp để trả về cho client:
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdf1 = new SimpleDateFormat(("yyyy-MM-dd"));
            String dateWithoutTime = null;
            try {
                dateWithoutTime = sdf.format(sdf1.parse(serviceContractDTO.getStartDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            serviceContractDTO.setStartDate(dateWithoutTime);

        });
        return serviceContractDTOs;
    }

    @Override
    public Optional<ServiceContractDTO> createServiceContract(Integer companyId, Integer serviceId, ServiceContractDTO serviceContractDTO) {
        // Kiểm tra xem service contract giữa company và service này đã có trong database chưa
        Optional<ServiceContractEntity> serviceContractEntityOptional = serviceContractRepository.findServiceContractEntityByCompany_IdAndService_Id(companyId,serviceId);
        if(serviceContractEntityOptional.isPresent()){
            return Optional.empty();
        }

        // Nếu pass bước kiểm tra, ko có contract nào của company và service đó thì lưu xuống database
        serviceContractDAO.createServiceContract(companyId,serviceId,serviceContractDTO);

        // Lấy lại thằng service đã lưu rồi trả về
        return Optional.of(
                modelMapper.map(serviceContractRepository.findServiceContractEntityByCompany_IdAndService_Id(companyId,serviceId)
                        ,ServiceContractDTO.class));
    }
}
