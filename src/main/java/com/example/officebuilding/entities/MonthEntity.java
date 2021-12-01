package com.example.officebuilding.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "month")
public class MonthEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int month;
    private int year;
}
