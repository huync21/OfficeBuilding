package com.example.officebuilding.service.company;

import com.example.officebuilding.dao.ServiceContractDAO;
import com.example.officebuilding.dtos.CompanyDTO;
import com.example.officebuilding.dtos.MonthDTO;
import com.example.officebuilding.dtos.MonthlyFeeOfCompanyDTO;
import com.example.officebuilding.dtos.ServiceContractDTO;
import com.example.officebuilding.entities.*;
import com.example.officebuilding.repository.*;
import com.example.officebuilding.service.contract.IContractService;
import com.example.officebuilding.service.service.IServiceService;
import com.example.officebuilding.service.service_contract.IServiceContractService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CompanyService implements ICompanyService{
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ICompanyRepository companyRepository;
    @Autowired
    private ICompanyEmployeeRepository companyEmployeeRepository;
    @Autowired
    private IServiceRepository serviceRepository;
    @Autowired
    private ServiceContractDAO serviceContractDAO;
    @Autowired
    private IServiceContractRepository serviceContractRepository;
    @Autowired
    private IMonthlyServiceBillRepository monthlyServiceBillRepository;
    @Autowired
    private IContractRepository contractRepository;
    @Autowired
    private IMonthlyBillRepository monthlyBillRepository;
    @Autowired
    private IContractService contractService;
    @Autowired
    private IServiceContractService serviceContractService;
    @Autowired
    private IMonthRepository monthRepository;

    @Override
    public List<CompanyDTO> findAll() {
        // Gọi repo lấy từ db
        List<CompanyEntity> companyEntities = companyRepository.findAll();

        //Chuyển các entities thành các đối tượng Data Transfer Object(DTO) rồi trả về cho controller
        List<CompanyDTO> companyDTOS = companyEntities.stream().map(companyEntity -> modelMapper.map(companyEntity, CompanyDTO.class))
                .collect(Collectors.toList());

        // count số employee cho từng công ty và tổng diện tích mặt bằng công ty đó thuê
        companyDTOS.forEach(companyDTO -> {
            companyDTO.setNumberOfEmployee(companyEmployeeRepository.countCompanyEmployeeEntitiesByCompany_Id(companyDTO.getId()));
            companyDTO.setSumOfRentedArea(contractService.getSumOfRentedArea(companyDTO.getId()));
        });

        return companyDTOS;
    }

    @Override
    public Optional<CompanyDTO> findById(Integer id) {
        // Gọi repo lấy dữ liệu entity từ db
        Optional<CompanyEntity> companyEntity = companyRepository.findById(id);

        //Chuyển entity thành DTO rồi trả về cho controller:
        return companyEntity.map(companyEntity1 -> modelMapper.map(companyEntity1, CompanyDTO.class));
    }

    @Override
    public CompanyDTO save(CompanyDTO companyDTO) {
        // Chuyển DTO thành entity
        CompanyEntity companyEntity = modelMapper.map(companyDTO, CompanyEntity.class);

        // Lưu xuống db và trả về đối tượng entity đã được cập nhật
        CompanyEntity updatedCompanyEntity = companyRepository.save(companyEntity);

        // Khi tạo công ty thì bắt buộc phải đăng ký những service bắt buộc có của tòa nhà
        // Đầu tiên phải tìm ra những service bắt buộc đó đã
        List<ServiceEntity> requiredServices = serviceRepository.findServiceEntitiesByRequired(1);
        // Lấy được ra list đó rồi thì đăng ký những service đó cho tòa nhà
        requiredServices.forEach(requiredService->{
            ServiceContractDTO serviceContractDTO = new ServiceContractDTO();
            serviceContractDTO.setStartDate(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
            serviceContractDTO.setDescription("Dịch vụ bắt buộc");
            serviceContractDAO.createServiceContract(companyEntity.getId(),requiredService.getId(),serviceContractDTO);
        });

        // Chuyển lại đối tượng entity đã được cập nhật sang DTO để trả về:
        return modelMapper.map(updatedCompanyEntity,CompanyDTO.class);
    }

    @Override
    public CompanyDTO update(CompanyDTO companyDTO) {
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



    @Override
    public List<MonthlyFeeOfCompanyDTO> getMonthlyFeeOfCompany(Integer monthId) {
        // Lấy ra tháng đó
        MonthDTO monthDTO = modelMapper.map(monthRepository.findById(monthId).get(), MonthDTO.class);
        // Lấy ra tất cả công ty rồi đổi sáng dto
        List<CompanyDTO> companyDTOS = companyRepository.findAll()
                .stream()
                .map(companyEntity -> modelMapper.map(companyEntity,CompanyDTO.class)).collect(Collectors.toList());

        // Lấy ra tổng tiền từng tháng của các công ty
        List<MonthlyFeeOfCompanyDTO> result = companyDTOS.stream()
                .map(companyDTO -> { // Với mỗi công ty, tính tổng tiền phải trả của tháng đó
                    MonthlyFeeOfCompanyDTO monthlyFeeOfCompanyDTO = new MonthlyFeeOfCompanyDTO();


                    // Lấy ra tất cả bill của service trong tháng đó của công ty rồi tính tổng tiền dịch vụ
                    List<MonthlyServiceBillEntity> monthlyServiceBillEntities = new ArrayList<>();
                    double totalFeeOfServices = 0;
                    // Lấy ra tát cả service contract của công ty
                    List<ServiceContractEntity> listServiceContractOfCompany =
                            serviceContractRepository.getServiceContractEntitiesByCompany_Id(companyDTO.getId());
                    // Lấy ra tất cả hóa đơn dịch vụ của công ty rồi add vào list các hóa đơn
                    listServiceContractOfCompany.forEach(serviceContract -> monthlyServiceBillEntities
                            .addAll(monthlyServiceBillRepository
                                    .findMonthlyServiceBillEntitiesByMonth_IdAndServiceContract_Id(monthId, serviceContract.getId())));
                    // Có được list các hóa đơn dịch vụ rồi thì tính tổng tiền dịch vụ
                    for (MonthlyServiceBillEntity monthlyServiceBill : monthlyServiceBillEntities)
                        totalFeeOfServices += monthlyServiceBill.getTotalAmount();


                    // Lấy ra tất cả các bill tiền thuê mặt bằng của công ty trong tháng đó rồi tính tổng tiền mặt bằng
                    List<MonthlyBillEntity> monthlyBillEntities = new ArrayList<>();
                    double totalFeeOfRentedArea = 0;
                    // Lấy ra tát cả contract của công ty
                    List<ContractEntity> contractEntityList =
                            contractRepository.getContractEntitiesByCompany_Id(companyDTO.getId());
                    // Lấy ra tất cả hóa đơn tiền thuê mặt bằng của công ty rồi add vào list các hóa đơn
                    contractEntityList.forEach(contract -> monthlyBillEntities
                            .addAll(monthlyBillRepository
                                    .findMonthlyBillEntitiesByContract_IdAndAndMonth_Id(contract.getId(),monthId)));
                    // Có được list các hóa đơn rồi thì tính tổng tiền mặt bằng tháng đó
                    for (MonthlyBillEntity monthlyBillEntity : monthlyBillEntities)
                        totalFeeOfRentedArea += monthlyBillEntity.getTotalAmount();

                    // Cộng vào lấy ra tổng tiền từng tháng
                    double totalSum = totalFeeOfRentedArea+totalFeeOfServices;
                    monthlyFeeOfCompanyDTO.setTotalAmount(totalSum);
                    monthlyFeeOfCompanyDTO.setMonth(monthDTO);

                    //Lấy diện ra số nhân viên và diện tích mặt bằng
                    companyDTO.setNumberOfEmployee(companyEmployeeRepository.countCompanyEmployeeEntitiesByCompany_Id(companyDTO.getId()));
                    companyDTO.setSumOfRentedArea(contractService.getSumOfRentedArea(companyDTO.getId()));
                    monthlyFeeOfCompanyDTO.setCompany(companyDTO);
                    
                    return monthlyFeeOfCompanyDTO;
                }).sorted((o1, o2) -> {// Sort theo thứ tự giảm dần tiền phải trả
                    double compare = o1.getTotalAmount() - o2.getTotalAmount();
                    if (compare < 0) {
                        return 1;
                    }
                    if (compare > 0) {
                        return -1;
                    }
                    return 0;
                }).collect(Collectors.toList());

        return result;
    }

    @Override
    public List<MonthlyFeeOfCompanyDTO> getFeeOfCompanies() {
        // Lấy ra tất cả công ty rồi đổi sáng dto
        List<CompanyDTO> companyDTOS = companyRepository.findAll()
                .stream()
                .map(companyEntity -> modelMapper.map(companyEntity,CompanyDTO.class)).collect(Collectors.toList());
        // Lấy ra thống kê tổng tiền tháng này tính đến thời điểm hiện tại của công ty
        return companyDTOS.stream().map(companyDTO -> {// với mỗi công ty, tính ra tổng tiền phải trả
                //Tính tổng tiền dịch vụ cho mỗi công ty:
                double totalFeeOfServices = serviceContractService
                        .findAllServiceContractOfCompany(companyDTO.getId())
                        .stream()
                        .map(serviceContract->serviceContract.getCurrentPrice())
                        .reduce(0.0,(sum,item)-> sum + item);

                //Tính tổng tiền thuê đất cho mỗi công ty:
                double totalFeeOfRentedArea = contractService
                        .getContractsByCompanyId(companyDTO.getId())
                        .stream()
                        .map(contract-> (contract.getFloor().getPricePerM2()*contract.getRentedArea()))
                        .reduce(0.0,(sum,item)-> sum + item);

                double totalFee = totalFeeOfServices+totalFeeOfRentedArea;

                //Lấy diện ra số nhân viên và diện tích mặt bằng
                companyDTO.setNumberOfEmployee(companyEmployeeRepository.countCompanyEmployeeEntitiesByCompany_Id(companyDTO.getId()));
                companyDTO.setSumOfRentedArea(contractService.getSumOfRentedArea(companyDTO.getId()));

                MonthlyFeeOfCompanyDTO monthlyFeeOfCompanyDTO = new MonthlyFeeOfCompanyDTO();
                monthlyFeeOfCompanyDTO.setCompany(companyDTO);
                monthlyFeeOfCompanyDTO.setTotalAmount(totalFee);
                return monthlyFeeOfCompanyDTO;
        }
        ).sorted((o1, o2) -> {// Sort theo thứ tự giảm dần tiền phải trả
            double compare = o1.getTotalAmount() - o2.getTotalAmount();
            if (compare < 0) {
                return 1;
            }
            if (compare > 0) {
                return -1;
            }
            return 0;
        }).collect(Collectors.toList());
    }

    @Override
    public List<CompanyDTO> findCompaniesByName(String name) {
        return companyRepository.findCompanyEntitiesByNameContaining(name)
                .stream().map(companyEntity -> modelMapper.map(companyEntity,CompanyDTO.class))
                .collect(Collectors.toList());
    }


}
