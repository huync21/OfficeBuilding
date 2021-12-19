package com.example.officebuilding.service.work;

import com.example.officebuilding.dao.WorkDAO;
import com.example.officebuilding.dtos.WorkDTO;
import com.example.officebuilding.entities.WorkEntity;
import com.example.officebuilding.repository.IWorkRepository;
import org.hibernate.jdbc.Work;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkService implements IWorkService{
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IWorkRepository workRepository;

    @Autowired
    private WorkDAO workDAO;

    @Override
    public List<WorkDTO> findAll() {
        // Gọi repo lấy từ db
        List<WorkEntity> workEntities = workRepository.findAll();

        //Chuyển các entities thành các đối tượng Data Transfer Object(DTO) rồi trả về cho controller
        List<WorkDTO> workDTOS = workEntities.stream().map(workEntity -> modelMapper.map(workEntity, WorkDTO.class))
                .collect(Collectors.toList());

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat sdf1 = new SimpleDateFormat(("yyyy-MM-dd"));
        workDTOS.forEach(workDTO -> {
            String sd = null, dd = null;
            try {
                sd = sdf.format((sdf1.parse(workDTO.getStartDate())));
                dd = sdf.format((sdf1.parse(workDTO.getDueDate())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            workDTO.setDueDate(dd);
            workDTO.setStartDate(sd);
        });
        return workDTOS;
    }

    @Override
    public Optional<WorkDTO> findById(Integer id) {
        // Gọi repo lấy dữ liệu entity từ db
        Optional<WorkEntity> workEntity = workRepository.findById(id);

        //Chuyển entity thành DTO rồi trả về cho controller:
        Optional<WorkDTO> workDTOOptional = workEntity.map(workEntity1 -> modelMapper.map(workEntity1, WorkDTO.class));
        return workDTOOptional;
    }

    @Override
    public List<WorkDTO> findWorkByBuildingEmployeeId(Integer empId) {
        List<WorkEntity> workEntityList = workRepository.findWorkEntitiesByBuildingEmployee_Id(empId);
        //Đổi sang dto
        return workEntityList.stream()
                .map(workEntity -> modelMapper.map(workEntity,WorkDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkDTO> findWorkByTitleContainingAndBuildingEmployeeId(String title, Integer id) {
        List<WorkEntity> workEntityList = workRepository.findWorkEntitiesByTitleContainingAndBuildingEmployee_Id(title, id);
        //Đổi sang dto
        return workEntityList.stream()
                .map(workEntity -> modelMapper.map(workEntity,WorkDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkDTO> findWorkByTitle(String title) {
        List<WorkDTO> workDTOS = workRepository.findWorkEntitiesByTitleContaining(title)
                .stream().map(workEntity -> modelMapper.map(workEntity,WorkDTO.class))
                .collect(Collectors.toList());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat sdf1 = new SimpleDateFormat(("yyyy-MM-dd"));
        workDTOS.forEach(workDTO -> {
            String sd = null, dd = null;
            try {
                sd = sdf.format((sdf1.parse(workDTO.getStartDate())));
                dd = sdf.format((sdf1.parse(workDTO.getDueDate())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            workDTO.setDueDate(dd);
            workDTO.setStartDate(sd);
        });
        return workDTOS;
    }

    @Override
    public WorkDTO save(WorkDTO workDTO) {
        // Chuyển DTO thành entity
        WorkEntity workEntity = modelMapper.map(workDTO, WorkEntity.class);

        // Lưu xuống db và trả về đối tượng entity đã được cập nhật
        WorkEntity updatedWorkEntity = workRepository.save(workEntity);

        // Chuyển lại đối tượng entity đã được cập nhật sang DTO để trả về:
        return modelMapper.map(updatedWorkEntity,WorkDTO.class);
    }

    @Override
    public void createNewWork(Integer empId, WorkDTO workDTO){
        System.out.println("-------------------------Service------------>"+workDTO.getTitle()+" "+workDTO.getDetail()+" "+workDTO.getStartDate()+" "+workDTO.getDueDate()+" "+workDTO.getAssigner()+" "+empId);
        workDAO.createNewWork(empId, workDTO);
    }

    @Override
    public void remove(Integer id) {
        workRepository.deleteById(id);
    }

    @Override
    public void updateWork(Integer workId, Integer empId, WorkDTO workDTO){
        workDAO.updateWork(workId, empId, workDTO);
    }
}
