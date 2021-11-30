package com.example.officebuilding.dtos;

import lombok.Data;

@Data
public class CompanyDTO {
    private int id;
    private String name;
    private String taxCode;
    private double authorizedCapital;
    private String phoneNo;
}
