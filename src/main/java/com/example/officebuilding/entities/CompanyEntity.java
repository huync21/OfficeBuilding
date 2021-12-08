package com.example.officebuilding.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;


@Data
@Table(name = "company")
@Entity
@NoArgsConstructor
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String taxCode;
    private double authorizedCapital;
    private String phoneNo;
    private String activeField;
}
