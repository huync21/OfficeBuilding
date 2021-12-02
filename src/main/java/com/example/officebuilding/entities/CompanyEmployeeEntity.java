package com.example.officebuilding.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "company_employee")
@Entity
@NoArgsConstructor
public class CompanyEmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String socialId;
    private String name;
    private Date dateOfBirth;
    private String phoneNo;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity company;
}
