package com.example.officebuilding.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "service_contract")
@NoArgsConstructor
public class ServiceContractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date startDate;
    private Date expireDate;
    private String description;
    @ManyToOne
    @JoinColumn(name = "service_contract_id")
    private ServiceEntity service;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;
}
