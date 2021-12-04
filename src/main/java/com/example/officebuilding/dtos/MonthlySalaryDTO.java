package com.example.officebuilding.dtos;

import lombok.Data;

@Data
public class MonthlySalaryDTO {
    private int id;
    private double salary;
    private MonthDTO month;
    private BuildingEmployeeDTO buildingEmployee;
}
