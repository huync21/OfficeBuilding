package com.example.officebuilding.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "building_employee")
@Entity
@NoArgsConstructor
public class BuildingEmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Date dateOfBirth;
    private String address;
    private String phoneNo;
    private String level;
    @ManyToOne
    @JoinColumn(name = "service_id",nullable = false )
    private ServiceEntity serviceEntity;
}
