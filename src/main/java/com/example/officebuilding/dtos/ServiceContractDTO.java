package com.example.officebuilding.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class ServiceContractDTO {
    private int id;
    private Date startDate;
    private Date cancelDate;
    private ServiceDTO service;
    private CompanyDTO company;
}
