package com.example.officebuilding.controller;

import com.example.officebuilding.entities.CompanyEmployeeEntity;
import com.example.officebuilding.service.company_employee.ICompanyEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/company-employee", produces = "application/json")
public class CompanyEmployeeController {
    @Autowired
    private ICompanyEmployeeService companyEmployeeService;

    @PostMapping
    public ResponseEntity<CompanyEmployeeEntity> post(@RequestBody CompanyEmployeeEntity companyEmployeeEntity){
        return new ResponseEntity<>(this.companyEmployeeService.save(companyEmployeeEntity), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<CompanyEmployeeEntity>> findAll() {
        return new ResponseEntity<>(this.companyEmployeeService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyEmployeeEntity> update(@PathVariable Integer id,@RequestBody CompanyEmployeeEntity companyEmployeeEntity){
        Optional<CompanyEmployeeEntity> companyEmployeeEntityOptional = this.companyEmployeeService.findById(id);

        return companyEmployeeEntityOptional.map(companyEmployeeOptional -> {
            companyEmployeeEntity.setId(companyEmployeeOptional.getId());
            return new ResponseEntity<>(this.companyEmployeeService.save(companyEmployeeEntity),HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CompanyEmployeeEntity> deleteCategory(@PathVariable Integer id) {
        Optional<CompanyEmployeeEntity> companyEmployeeEntityOptional = this.companyEmployeeService.findById(id);
        return companyEmployeeEntityOptional.map(companyEmployeeOptional -> {
            this.companyEmployeeService.remove(id);
            return new ResponseEntity<>(companyEmployeeOptional, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
