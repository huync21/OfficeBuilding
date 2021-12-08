package com.example.officebuilding.service.contract;

import com.example.officebuilding.dao.ContractDAO;
import com.example.officebuilding.dtos.ContractDTO;
import com.example.officebuilding.entities.ContractEntity;
import com.example.officebuilding.entities.FloorEntity;
import com.example.officebuilding.repository.IContractRepository;
import com.example.officebuilding.repository.IFloorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContractService implements IContractService{
    @Autowired
    private ContractDAO contractDAO;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IContractRepository contractRepository;
    @Autowired
    private IFloorRepository floorRepository;

    @Override
    public List<ContractDTO> findAll() {
        // Lấy list entities từ db lên
        List<ContractEntity> contractEntities = contractRepository.findAll();

        // Đổi list entities đó về dto rồi trả về
        List<ContractDTO> contractDTOs = contractEntities.stream()
                .map(contractEntity -> modelMapper.map(contractEntity, ContractDTO.class))
                .collect(Collectors.toList());
        return contractDTOs;
    }

    @Override
    public Optional<ContractDTO> findById(Integer id) {
        // Lấy entity từ db lên
        Optional<ContractEntity> contractEntity = contractRepository.findById(id);

        // Chuyển sang dto rồi trả về
        Optional<ContractDTO> contractDTO = contractEntity
                .map(contractEntity1 -> modelMapper.map(contractEntity1, ContractDTO.class));
        return contractDTO;
    }

    @Override
    public ContractDTO save(ContractDTO contractDTO) {
        // chuyển từ DTO sang entity:
        ContractEntity contractEntity = modelMapper.map(contractDTO, ContractEntity.class);

        // save xuống db:
        ContractEntity updatedEntity = contractRepository.save(contractEntity);

        // chuyển lại đối tượng đã được update sang dto và trả về:
        return modelMapper.map(updatedEntity,ContractDTO.class);
    }

    @Override
    public void remove(Integer id) {
        contractRepository.deleteById(id);
    }

    @Override
    public double getSumOfRentedArea(Integer companyId) {
        double result = 0;
        List<ContractEntity> list = contractRepository.getContractEntitiesByCompany_Id(companyId);
        for (ContractEntity contract: list){
            result+=contract.getRentedArea();
        }
        return result;
    }
    @Override
    public double getSumOfRentedAreaFloor(Integer floorId) {
        double result = 0;
        List<ContractEntity> list = contractRepository.getContractEntitiesByFloor_Id(floorId);
        for (ContractEntity contract: list){
            result+=contract.getRentedArea();
        }
        return result;
    }

    @Override
    public void createContract(Integer companyId, Integer floorId, ContractDTO contractDTO) {
        contractDAO.createContract(companyId, floorId, contractDTO);
    }

    @Override
    public List<ContractDTO> getContractsByFloorId(Integer floorId) {
        return contractRepository.getContractEntitiesByFloor_Id(floorId).stream().map(
                contractEntity -> modelMapper.map(contractEntity,ContractDTO.class)
        ).collect(Collectors.toList());
    }

    @Override
    public List<ContractDTO> getContractsByCompanyId(Integer companyId) {
        return contractRepository
                .getContractEntitiesByCompany_Id(companyId)
                .stream()
                .map(contractEntity -> {
                    ContractDTO contractDTO = modelMapper.map(contractEntity, ContractDTO.class);
                    contractDTO.setCurrentPrice(
                            contractEntity.getRentedArea()*contractEntity.getFloor().getPricePerM2());
                    return contractDTO;
                }
        ).collect(Collectors.toList());
    }


}
