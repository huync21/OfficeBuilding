package com.example.officebuilding.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "contract")
@NoArgsConstructor
public class ContractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date rentedDate;
    private Date expiredDate;
    private double rentPrice;

    @OneToMany(mappedBy = "contractEntity")
    private List<RentedAreaEntity> rentedAreaEntities;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity companyEntity;

    @OneToMany(mappedBy = "contractEntity")
    private List<MonthlyBillEntity> monthlyBillEntities;
}
