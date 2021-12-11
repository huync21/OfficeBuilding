package com.example.officebuilding.service.monthly_service_bill;

import com.example.officebuilding.dtos.*;
import com.example.officebuilding.entities.ContractEntity;
import com.example.officebuilding.entities.MonthlyServiceBillEntity;
import com.example.officebuilding.entities.ServiceContractEntity;
import com.example.officebuilding.repository.ICompanyEmployeeRepository;
import com.example.officebuilding.repository.IContractRepository;
import com.example.officebuilding.repository.IMonthlyServiceBillRepository;
import com.example.officebuilding.repository.IServiceContractRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MonthlyServiceBillService implements IMonthlyServiceBillService{
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IMonthlyServiceBillRepository monthlyServiceBillRepository;

    @Autowired
    private ICompanyEmployeeRepository companyEmployeeRepository;

    @Autowired
    private IContractRepository contractRepository;

    @Autowired
    private IServiceContractRepository serviceContractRepository;

    @Override
    public List<MonthlyServiceBillDTO> findAll() {
        // Gọi repo lấy từ db
        List<MonthlyServiceBillEntity> monthlyServiceBillEntities = monthlyServiceBillRepository.findAll();

        //Chuyển các entities thành các đối tượng Data Transfer Object(DTO) rồi trả về cho controller
        List<MonthlyServiceBillDTO> monthlyServiceBillDTOS = monthlyServiceBillEntities
                .stream()
                .map(monthlyServiceBillEntity ->
                        modelMapper.map(monthlyServiceBillEntity, MonthlyServiceBillDTO.class))
                .collect(Collectors.toList());
        return monthlyServiceBillDTOS;
    }

    @Override
    public Optional<MonthlyServiceBillDTO> findById(Integer id) {
        // Gọi repo lấy dữ liệu entity từ db
        Optional<MonthlyServiceBillEntity> monthlyServiceBillEntity =
                monthlyServiceBillRepository.findById(id);

        //Chuyển entity thành DTO rồi trả về cho controller:
        Optional<MonthlyServiceBillDTO> monthlyServiceBillDTOOptional =
                monthlyServiceBillEntity
                        .map(monthlyServiceBillEntity1 ->
                                modelMapper.map(monthlyServiceBillEntity1, MonthlyServiceBillDTO.class));
        return monthlyServiceBillDTOOptional;
    }

    @Override
    public MonthlyServiceBillDTO save(MonthlyServiceBillDTO monthlyServiceBillDTO) {
        // Chuyển DTO thành entity
        MonthlyServiceBillEntity monthlyServiceBillEntity =
                modelMapper.map(monthlyServiceBillDTO, MonthlyServiceBillEntity.class);

        // Lưu xuống db và trả về đối tượng entity đã được cập nhật
        MonthlyServiceBillEntity updatedMonthlyServiceBillEntity =
                monthlyServiceBillRepository.save(monthlyServiceBillEntity);

        // Chuyển lại đối tượng entity đã được cập nhật sang DTO để trả về:
        return modelMapper.map(updatedMonthlyServiceBillEntity,MonthlyServiceBillDTO.class);
    }


    @Override
    public void remove(Integer id) {
        monthlyServiceBillRepository.deleteById(id);
    }

    @Override
    public double calculateMoney(Date startDate, Date endDate, ServiceContractDTO serviceContractDTO) {

            //Lấy ra giá cơ bản của dịch vụ trong 1 tháng với những trường hợp dưới 100m2 mặt bằng và <10 người trong công ty:
            double basicPricePerMonth = serviceContractDTO.getService().getPrice();

            // Đếm số ngày sử dụng trong tháng
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date start = startDate;
            Date end = endDate;
            int numberOfDaysUsingService = end.getDate() - start.getDate();

            // Lấy ra số ngày của tháng đó
            int month = start.getMonth()+1;
            int year = start.getYear()+1900;
            YearMonth yearMonthObject = YearMonth.of(year, month);
            int daysInMonth = yearMonthObject.lengthOfMonth();

            // Lấy ra company và đếm số nhân viên của công ty
            CompanyDTO companyDTO = serviceContractDTO.getCompany();
            int numberOfEmployee = companyEmployeeRepository
                    .countCompanyEmployeeEntitiesByCompany_Id(companyDTO.getId());

            // Lấy ra các hợp đồng mặt bằng của công ty đó và đếm tổng diện tích mặt bằng công ty đó thuê
            List<ContractEntity> contractEntities = contractRepository
                    .getContractEntitiesByCompany_Id(companyDTO.getId());
            double sumOfRentedArea = 0;
            for(ContractEntity contract: contractEntities) sumOfRentedArea+= contract.getRentedArea();

            // Tính ra xem có lớn hơn 10 người trong công ty hay hơn 100m2 ko.
            // Nếu có thì cứ thêm 5 người cộng 5%(tức là 1 người chênh thì thêm 1%)
            // , thêm 10 m2 cộng 5%(tức là 1 m2 thì cộng 5/10=0.5%) vào giá cơ bản
            double addPercentage = 0;
            if(numberOfEmployee>10) addPercentage += numberOfEmployee-10;
            if(sumOfRentedArea>100) addPercentage += (sumOfRentedArea-100)*1/2;
            basicPricePerMonth *= (100+addPercentage)/100;

            // Nhân với tỉ lệ số ngày sử dụng trên số ngày của tháng
            basicPricePerMonth = basicPricePerMonth*numberOfDaysUsingService/daysInMonth;
            // Làm tròn
            basicPricePerMonth = (double) Math.floor(basicPricePerMonth * 1000) / 1000;
            return basicPricePerMonth>=0 ? basicPricePerMonth : 0;

    }

    @Override
    public List<MonthlyServiceBillDTO> findMonthlyServiceBillsOfCompanyInAMonth(Integer companyId, Integer monthId) {
        List<ServiceContractEntity> serviceContractEntitiesByCompany_id = serviceContractRepository.getServiceContractEntitiesByCompany_Id(companyId);
        List<MonthlyServiceBillDTO> result = new ArrayList<>();
        serviceContractEntitiesByCompany_id.stream()
                .forEach(serviceContract -> {
                    result.addAll(monthlyServiceBillRepository
                            .findMonthlyServiceBillEntitiesByMonth_IdAndServiceContract_Id(monthId,serviceContract.getId())
                            .stream().map(monthlyServiceBillEntity -> {
                                return modelMapper.map(monthlyServiceBillEntity,MonthlyServiceBillDTO.class);
                            }).collect(Collectors.toList()));
                });
        return result;
    }

}
