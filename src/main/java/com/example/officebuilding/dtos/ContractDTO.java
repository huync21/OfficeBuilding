package com.example.officebuilding.dtos;

import lombok.Data;

import java.util.Date;


@Data
public class ContractDTO {
    private int id;
    private Date rentedDate;
    private Date expiredDate;
    private double rentedArea;
    private String description;
    private int isCanceled;
    private CompanyDTO company;
    private FloorDTO floor;
}
