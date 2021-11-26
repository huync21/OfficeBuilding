package com.example.officebuilding.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Table(name = "company")
@Entity
@NoArgsConstructor
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String type;

    @OneToMany(mappedBy = "companyEntity")
    private List<CompanyEmployeeEntity> companyEmployeeEntities;

    @OneToMany(mappedBy = "companyEntity")
    private List<ServiceContractEntity> serviceContractEntities;

    @OneToMany(mappedBy = "companyEntity")
    private List<ContractEntity> contractEntities;
}
