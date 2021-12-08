package com.example.officebuilding.dtos;

import lombok.Data;

@Data
public class FloorDTO {
    private int id;
    private String name;
    private double pricePerM2;
    private double groundArea;
}
