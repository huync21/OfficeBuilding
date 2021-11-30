package com.example.officebuilding.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class BuildingEmployeeDTO {
    private int id;
    private String name;
    private Date dateOfBirth;
    private String address;
    private String phoneNo;
    private ServiceDTO service;
    private SalaryDTO salary;
}
