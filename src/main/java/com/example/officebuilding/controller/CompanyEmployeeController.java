package com.example.officebuilding.controller;

import com.example.officebuilding.dao.CompanyEmployeeDAO;
import com.example.officebuilding.dtos.CompanyEmployeeDTO;
import com.example.officebuilding.service.company_employee.ICompanyEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/company-employee", produces = "application/json")
public class CompanyEmployeeController {
    @Autowired
    private ICompanyEmployeeService companyEmployeeService;

    @Autowired
    private CompanyEmployeeDAO companyEmployeeDAO;

    @PostMapping
    public ResponseEntity<CompanyEmployeeDTO> createNewCompanyEmployee(@RequestBody CompanyEmployeeDTO companyEmployeeDTO){
        return new ResponseEntity<>(companyEmployeeService.save(companyEmployeeDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CompanyEmployeeDTO>> getAllCompanyEmployees() {
        return new ResponseEntity<>(companyEmployeeService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyEmployeeDTO> updateCompanyEmployee(@PathVariable Integer id,@RequestBody CompanyEmployeeDTO companyEmployeeDTO){
        // Lấy thử đối tượng có id đó ra xem tồn tại chưa để cập nhật, ko thì trả về status not found
        Optional<CompanyEmployeeDTO> companyEmployeeDTOOptional = companyEmployeeService.findById(id);

        return companyEmployeeDTOOptional.map(companyEmployeeDTO1 -> {
            companyEmployeeDTO.setId(companyEmployeeDTO1.getId());
            CompanyEmployeeDTO updatedCompanyEmployee = companyEmployeeService.save(companyEmployeeDTO);
            return new ResponseEntity<>(updatedCompanyEmployee,HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CompanyEmployeeDTO> deleteCompanyEmployee(@PathVariable Integer id) {
        // Lấy thử đối tượng có id đó ra xem tồn tại chưa để xóa, ko thì trả về status not found
        Optional<CompanyEmployeeDTO> companyEmployeeDTOOptional = companyEmployeeService.findById(id);
        return companyEmployeeDTOOptional.map(companyEmployeeDTO -> {
            companyEmployeeService.remove(id);
            return new ResponseEntity<>(companyEmployeeDTO, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/companyId={id}")
    public ResponseEntity<List<CompanyEmployeeDTO>> findAllEmployeeOfCompany(@PathVariable Integer id) {
        return new ResponseEntity<>(companyEmployeeService.findAllEmployeeOfCompany(id), HttpStatus.OK);
    }

    @GetMapping("/employeeCount/companyId={id}")
    public ResponseEntity<Integer> countEmployees(@PathVariable Integer id) {
        return new ResponseEntity<>(companyEmployeeService.countCompanyEmployeesByCompanyID(id), HttpStatus.OK);
    }

    @PostMapping("/companyId={id}")
    public void insertEmployeeOfCompany(@PathVariable Integer id, @RequestBody CompanyEmployeeDTO companyEmployeeDTO){
        companyEmployeeDAO.insertCompanyEmployeeByCompanyId(id,companyEmployeeDTO);
    }

    @GetMapping("/get-by-name")
    public ResponseEntity<List<CompanyEmployeeDTO>> getEmployeesByName(@RequestParam String empName,@RequestParam Integer companyId){
        return new ResponseEntity<>(companyEmployeeService.findEmployeesByNameAndCompanyId(empName,companyId),HttpStatus.OK);
    }


}
