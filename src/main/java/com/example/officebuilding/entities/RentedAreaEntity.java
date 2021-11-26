package com.example.officebuilding.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "rented_area")
@NoArgsConstructor
public class RentedAreaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double groundArea;

    @ManyToOne
    @JoinColumn(name = "floor_id")
    private FloorEntity floorEntity;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private ContractEntity contractEntity;
}
