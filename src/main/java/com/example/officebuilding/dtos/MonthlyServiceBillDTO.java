package com.example.officebuilding.dtos;

import lombok.Data;

@Data
public class MonthlyServiceBillDTO {
    private int id;
    private int month;
    private int year;
    private double totalAmount;
    private ServiceContractDTO serviceContract;
}
