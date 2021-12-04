package com.example.officebuilding.dtos;

import lombok.Data;

@Data
public class MonthlyFeeOfCompanyDTO{
    private MonthDTO month;
    private double totalAmount;
    private CompanyDTO company;
}
