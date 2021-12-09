package com.example.officebuilding.controller;

import com.example.officebuilding.dtos.CompanyDTO;
import com.example.officebuilding.dtos.MonthlyFeeOfCompanyDTO;
import com.example.officebuilding.service.company.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/company", produces = "application/json")
public class CompanyController {
    @Autowired
    private ICompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyDTO> createNewCompany(@RequestBody CompanyDTO companyDTO){
        return new ResponseEntity<>(companyService.save(companyDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAllCompanys() {
        return new ResponseEntity<>(companyService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Integer id){
        Optional<CompanyDTO> company = companyService.findById(id);
        return company.map(companyDTO -> {
            return new ResponseEntity<>(companyDTO,HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDTO> updateCompany(@PathVariable Integer id,@RequestBody CompanyDTO companyDTO){
        // Lấy thử đối tượng có id đó ra xem tồn tại chưa để cập nhật, ko thì trả về status not found
        Optional<CompanyDTO> companyDTOOptional = companyService.findById(id);

        return companyDTOOptional.map(companyDTO1 -> {
            companyDTO.setId(companyDTO1.getId());
            CompanyDTO updatedCompany = companyService.update(companyDTO);
            return new ResponseEntity<>(updatedCompany,HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CompanyDTO> deleteCompany(@PathVariable Integer id) {
        // Lấy thử đối tượng có id đó ra xem tồn tại chưa để xóa, ko thì trả về status not found
        Optional<CompanyDTO> companyDTOOptional = companyService.findById(id);
        return companyDTOOptional.map(companyDTO -> {
            companyService.remove(id);
            return new ResponseEntity<>(companyDTO, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/monthly_total_fee/monthId={monthId}")
    public ResponseEntity<List<MonthlyFeeOfCompanyDTO>> getMonthlyFeeOfAllCompanies(@PathVariable Integer monthId){
        return new ResponseEntity<>(companyService.getMonthlyFeeOfCompany(monthId),HttpStatus.OK);
    }

    @GetMapping("monthly-fees-till-this-moment")
    public ResponseEntity<List<MonthlyFeeOfCompanyDTO>> getMonthlyFeesTillThisMoment(){
        return new ResponseEntity<>(companyService.getFeeOfCompanies(),HttpStatus.OK);
    }

    @GetMapping("/name={name}")
    public ResponseEntity<List<CompanyDTO>> getCompaniesByName(@PathVariable String name){
        return new ResponseEntity<>(companyService.findCompaniesByName(name),HttpStatus.OK);
    }
}
