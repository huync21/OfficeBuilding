package com.example.officebuilding.service.floor;

import com.example.officebuilding.dtos.FloorDTO;
import com.example.officebuilding.entities.FloorEntity;
import com.example.officebuilding.repository.IFloorRepository;
import com.example.officebuilding.service.contract.IContractService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FloorService implements IFloorService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IFloorRepository floorRepository;

    @Autowired
    private IContractService contractService;

    @Override
    public List<FloorDTO> findAll() {
        // Gọi repo lấy từ db
        List<FloorEntity> floorEntities = floorRepository.findAll();

        //Chuyển các entities thành các đối tượng Data Transfer Object(DTO) rồi trả về cho controller
        List<FloorDTO> floorDTOS = floorEntities.stream().map(floorEntity -> modelMapper.map(floorEntity, FloorDTO.class))
                .collect(Collectors.toList());
        return floorDTOS;
    }

    @Override
    public Optional<FloorDTO> findById(Integer id) {
        // Gọi repo lấy dữ liệu entity từ db
        Optional<FloorEntity> floorEntity = floorRepository.findById(id);

        //Chuyển entity thành DTO rồi trả về cho controller:
        Optional<FloorDTO> floorDTOOptional = floorEntity.map(floorEntity1 -> modelMapper.map(floorEntity1, FloorDTO.class));
        return floorDTOOptional;
    }

    @Override
    public FloorDTO save(FloorDTO floorDTO) {
        // Chuyển DTO thành entity
        FloorEntity floorEntity = modelMapper.map(floorDTO, FloorEntity.class);

        // Lưu xuống db và trả về đối tượng entity đã được cập nhật
        FloorEntity updatedFloorEntity = floorRepository.save(floorEntity);

        // Chuyển lại đối tượng entity đã được cập nhật sang DTO để trả về:
        return modelMapper.map(updatedFloorEntity,FloorDTO.class);
    }


    @Override
    public void remove(Integer id) {
        floorRepository.deleteById(id);
    }

    @Override
    public double getTheRestAreaOfFloor(Integer floorId) {
        Optional<FloorEntity> floor = floorRepository.findById(floorId);
        double theWholeAreaOfFloor = floor.get().getGroundArea();
        double rentedArea = contractService.getSumOfRentedAreaFloor(floorId);
        return theWholeAreaOfFloor-rentedArea;
    }

}
