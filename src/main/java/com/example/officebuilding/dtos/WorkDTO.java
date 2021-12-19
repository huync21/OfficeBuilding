package com.example.officebuilding.dtos;

import lombok.Data;

@Data
public class WorkDTO {
    private int id;
    private String title;
    private String detail;
    private String startDate;
    private String dueDate;
    private String assigner;
    private BuildingEmployeeDTO buildingEmployee;
}
