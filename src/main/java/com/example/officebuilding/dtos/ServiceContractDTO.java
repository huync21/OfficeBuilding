package com.example.officebuilding.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class ServiceContractDTO {
    private int id;
    private String startDate;
    private String description;
    private double currentPrice;
    private ServiceDTO service;
    private CompanyDTO company;
}
