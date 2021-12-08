package com.example.officebuilding.dtos;

import lombok.Data;

import java.util.Date;


@Data
public class ContractDTO {
    private int id;
    private String rentedDate;
    private String expiredDate;
    private double rentedArea;
    private String description;
    private int isCanceled;
    private String position;
    private CompanyDTO company;
    private FloorDTO floor;
}
