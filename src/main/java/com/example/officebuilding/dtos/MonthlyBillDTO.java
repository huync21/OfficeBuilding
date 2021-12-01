package com.example.officebuilding.dtos;

import lombok.Data;

@Data
public class MonthlyBillDTO {
    private int id;
    private double totalAmount;
    private ContractDTO contract;
    private MonthDTO month;
}
