package com.example.officebuilding.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;


@Data
@Table(name = "service")
@Entity
@NoArgsConstructor
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String type;
    private double price;
    private int required;
}
