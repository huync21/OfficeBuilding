package com.example.officebuilding.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@Entity
@Table(name = "floor")
@NoArgsConstructor
public class FloorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double pricePerM2;
    private double groundArea;

}
