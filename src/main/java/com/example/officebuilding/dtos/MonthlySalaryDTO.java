package com.example.officebuilding.dtos;

import lombok.Data;

@Data
public class MonthlySalaryDTO {
    private int id;
    private double salary;
    private String empName;
    private String address;
    private String phoneNo;
    private String position;
    private int salaryLevel;
    private MonthDTO month;
    private BuildingEmployeeDTO buildingEmployee;
}
