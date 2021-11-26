package com.example.officebuilding.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "monthly_bill")
@NoArgsConstructor
public class MonthlyBillEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int month;
    private int year;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private ContractEntity contractEntity;
}
