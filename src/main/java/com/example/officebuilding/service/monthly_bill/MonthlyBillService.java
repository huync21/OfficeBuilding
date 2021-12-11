package com.example.officebuilding.service.monthly_bill;

import com.example.officebuilding.dtos.MonthlyBillDTO;
import com.example.officebuilding.repository.IContractRepository;
import com.example.officebuilding.repository.IMonthlyBillRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonthlyBillService implements IMonthlyBillService{
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IContractRepository contractRepository;
    @Autowired
    private IMonthlyBillRepository monthlyBillRepository;
    @Override
    public List<MonthlyBillDTO> findMonthlyBillsOfCompanyInAMonth(Integer companyId, Integer monthId) {
        List<MonthlyBillDTO> result = new ArrayList<>();

        contractRepository.getContractEntitiesByCompany_Id(companyId)
                .stream().forEach(
                        contractEntity -> {
                            result.addAll(monthlyBillRepository
                                    .findMonthlyBillEntitiesByContract_IdAndAndMonth_Id(contractEntity.getId(),monthId)
                                    .stream().map(monthlyBillEntity -> {
                                        return modelMapper.map(monthlyBillEntity,MonthlyBillDTO.class);
                                    }).collect(Collectors.toList()));
                        }
                );

        return result;
    }
}
