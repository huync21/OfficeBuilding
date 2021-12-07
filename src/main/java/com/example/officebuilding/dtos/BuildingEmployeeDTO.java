package com.example.officebuilding.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class BuildingEmployeeDTO {
    private int id;
    private String name;
    private String dateOfBirth;
    private String address;
    private String phoneNo;
    private String position;
    private SalaryDTO salary;
}
