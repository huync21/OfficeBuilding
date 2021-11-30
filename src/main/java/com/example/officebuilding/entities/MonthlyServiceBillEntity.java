package com.example.officebuilding.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "monthly_service_bill")
@NoArgsConstructor
public class MonthlyServiceBillEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int month;
    private int year;
    private double totalAmount;
    @ManyToOne
    @JoinColumn(name = "service_contract_id", nullable = false)
    private ServiceContractEntity serviceContract;
}
