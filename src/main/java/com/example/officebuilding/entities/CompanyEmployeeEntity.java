package com.example.officebuilding.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "copany_employee_entity")
@Entity
@NoArgsConstructor
public class CompanyEmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String socialID;
    private String name;
    private Date dateOfBirth;
    private String phoneNo;
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity companyEntity;
}
