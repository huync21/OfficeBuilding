package com.example.officebuilding.service.monthly_salary;

import com.example.officebuilding.dtos.MonthlySalaryDTO;
import com.example.officebuilding.entities.MonthlySalaryEntity;
import com.example.officebuilding.repository.IMonthlySalaryRepository;
import com.example.officebuilding.util.DateUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MonthlySalaryService implements IMonthlySalaryService{
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IMonthlySalaryRepository monthlySalaryRepository;

    @Override
    public List<MonthlySalaryDTO> findAll() {
        // Gọi repo lấy từ db
        List<MonthlySalaryEntity> monthlySalaryEntities = monthlySalaryRepository.findAll();

        //Chuyển các entities thành các đối tượng Data Transfer Object(DTO) rồi trả về cho controller
        List<MonthlySalaryDTO> monthlySalaryDTOS = monthlySalaryEntities.stream().map(monthlySalaryEntity -> modelMapper.map(monthlySalaryEntity, MonthlySalaryDTO.class))
                .collect(Collectors.toList());
        return monthlySalaryDTOS;
    }

    @Override
    public Optional<MonthlySalaryDTO> findById(Integer id) {
        // Gọi repo lấy dữ liệu entity từ db
        Optional<MonthlySalaryEntity> monthlySalaryEntity = monthlySalaryRepository.findById(id);

        //Chuyển entity thành DTO rồi trả về cho controller:
        Optional<MonthlySalaryDTO> monthlySalaryDTOOptional = monthlySalaryEntity.map(monthlySalaryEntity1 -> modelMapper.map(monthlySalaryEntity1, MonthlySalaryDTO.class));
        return monthlySalaryDTOOptional;
    }

    @Override
    public MonthlySalaryDTO save(MonthlySalaryDTO monthlySalaryDTO) {
        // Chuyển DTO thành entity
        MonthlySalaryEntity monthlySalaryEntity = modelMapper.map(monthlySalaryDTO, MonthlySalaryEntity.class);

        // Lưu xuống db và trả về đối tượng entity đã được cập nhật
        MonthlySalaryEntity updatedMonthlySalaryEntity = monthlySalaryRepository.save(monthlySalaryEntity);

        // Chuyển lại đối tượng entity đã được cập nhật sang DTO để trả về:
        return modelMapper.map(updatedMonthlySalaryEntity,MonthlySalaryDTO.class);
    }


    @Override
    public void remove(Integer id) {
        monthlySalaryRepository.deleteById(id);
    }

    @Override
    public List<MonthlySalaryDTO> getMonthlySalariesByMonthId(Integer monthId) {
        List<MonthlySalaryDTO> monthlySalaryDTOS = monthlySalaryRepository.findMonthlySalaryEntitiesByMonth_Id(monthId)
                .stream().map(monthlySalaryEntity ->
                        modelMapper.map(monthlySalaryEntity, MonthlySalaryDTO.class))
                .collect(Collectors.toList());
        // Format lại ngày sinh cho đẹp
        monthlySalaryDTOS.forEach(monthlySalaryDTO -> {monthlySalaryDTO.getBuildingEmployee()
                .setDateOfBirth(DateUtil.getDateWithoutTime(monthlySalaryDTO
                                .getBuildingEmployee().getDateOfBirth()));
        });
        return  monthlySalaryDTOS;
    }
}
