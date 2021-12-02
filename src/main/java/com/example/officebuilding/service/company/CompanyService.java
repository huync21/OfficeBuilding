package com.example.officebuilding.service.company;

import com.example.officebuilding.dtos.CompanyDTO;
import com.example.officebuilding.entities.CompanyEntity;
import com.example.officebuilding.repository.ICompanyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyService implements ICompanyService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ICompanyRepository companyRepository;

    @Override
    public List<CompanyDTO> findAll() {
        // Gọi repo lấy từ db
        List<CompanyEntity> companyEntities = companyRepository.findAll();

        //Chuyển các entities thành các đối tượng Data Transfer Object(DTO) rồi trả về cho controller
        List<CompanyDTO> companyDTOS = companyEntities.stream().map(companyEntity -> modelMapper.map(companyEntity, CompanyDTO.class))
                .collect(Collectors.toList());
        return companyDTOS;
    }

    @Override
    public Optional<CompanyDTO> findById(Integer id) {
        // Gọi repo lấy dữ liệu entity từ db
        Optional<CompanyEntity> companyEntity = companyRepository.findById(id);

        //Chuyển entity thành DTO rồi trả về cho controller:
        Optional<CompanyDTO> companyDTOOptional = companyEntity.map(companyEntity1 -> modelMapper.map(companyEntity1, CompanyDTO.class));
        return companyDTOOptional;
    }

    @Override
    public CompanyDTO save(CompanyDTO companyDTO) {
        // Chuyển DTO thành entity
        CompanyEntity companyEntity = modelMapper.map(companyDTO, CompanyEntity.class);

        // Lưu xuống db và trả về đối tượng entity đã được cập nhật
        CompanyEntity updatedCompanyEntity = companyRepository.save(companyEntity);

        // Chuyển lại đối tượng entity đã được cập nhật sang DTO để trả về:
        return modelMapper.map(updatedCompanyEntity,CompanyDTO.class);
    }


    @Override
    public void remove(Integer id) {
        companyRepository.deleteById(id);
    }
}