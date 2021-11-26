package com.example.officebuilding.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
    @OneToMany(mappedBy = "serviceEntity")
    private List<BuildingEmployeeEntity> buildingEmployeeEntities;

    @OneToMany(mappedBy = "serviceEntity")
    private List<ServiceContractEntity> serviceContractEntities;
}
