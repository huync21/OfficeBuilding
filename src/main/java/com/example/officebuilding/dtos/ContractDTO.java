package com.example.officebuilding.dtos;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ContractDTO {
    private int id;
    private Date rentedDate;
    private Date expiredDate;
    private double rentPrice;
    private CompanyDTO company;
    private List<RentedAreaDTO> rentedArea;
}
