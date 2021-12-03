package com.example.officebuilding.dtos;

import lombok.Data;
import java.util.Date;

@Data
public class CompanyEmployeeDTO {
    private int id;
    private String socialId;
    private String name;
    private Date dateOfBirth;
    private String phoneNo;
    private CompanyDTO company;
}
