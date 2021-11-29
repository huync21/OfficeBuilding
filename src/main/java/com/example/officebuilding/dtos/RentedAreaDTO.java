package com.example.officebuilding.dtos;

import lombok.Data;

@Data
public class RentedAreaDTO {
    private int id;
    private double groundArea;
    private FloorDTO floor;
}
