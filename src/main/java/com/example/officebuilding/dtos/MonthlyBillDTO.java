package com.example.officebuilding.dtos;

import lombok.Data;

@Data
public class MonthlyBillDTO {
    private int id;
    private int month;
    private int year;
    private ContractDTO contract;
}
