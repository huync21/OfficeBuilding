package com.example.officebuilding.dtos;

import lombok.Data;

@Data
public class SalaryDTO {
    private int id;
    private int salaryLevel;
    private ServiceDTO service;
}
