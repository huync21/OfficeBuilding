package com.example.officebuilding.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "floor")
@NoArgsConstructor
public class FloorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double pricePerM2;
    private double groundArea;

    @OneToMany(mappedBy = "floorEntity",fetch = FetchType.LAZY)
    private List<RentedAreaEntity> rentedAreas;
}
