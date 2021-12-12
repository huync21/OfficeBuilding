package com.example.officebuilding.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@Entity
@Table(name = "monthly_salary")
@NoArgsConstructor
public class MonthlySalaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double salary;
    private String empName;
    private String address;
    private String phoneNo;
    private String position;
    private int salaryLevel;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "building_employee_id",nullable = false)
    private BuildingEmployeeEntity buildingEmployee;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "month_id",nullable = false)
    private MonthEntity month;
}
