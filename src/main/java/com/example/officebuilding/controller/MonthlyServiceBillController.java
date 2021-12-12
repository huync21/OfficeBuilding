package com.example.officebuilding.controller;

import com.example.officebuilding.dtos.MonthlyServiceBillDTO;
import com.example.officebuilding.dtos.ServiceContractDTO;
import com.example.officebuilding.service.monthly_service_bill.IMonthlyServiceBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/monthlyServiceBills", produces = "application/json")
public class MonthlyServiceBillController {
    @Autowired
    private IMonthlyServiceBillService monthlyServiceBillService;

    @PostMapping
    public ResponseEntity<MonthlyServiceBillDTO> createNewMonthlyServiceBill(@RequestBody MonthlyServiceBillDTO monthlyServiceBillDTO){
        return new ResponseEntity<>(monthlyServiceBillService.save(monthlyServiceBillDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MonthlyServiceBillDTO>> getAllMonthlyServiceBills() {
        return new ResponseEntity<>(monthlyServiceBillService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MonthlyServiceBillDTO> updateMonthlyServiceBill(@PathVariable Integer id, @RequestBody MonthlyServiceBillDTO monthlyServiceBillDTO){
        // Lấy thử đối tượng có id đó ra xem tồn tại chưa để cập nhật, ko thì trả về status not found
        Optional<MonthlyServiceBillDTO> monthlyServiceBillDTOOptional = monthlyServiceBillService.findById(id);

        return monthlyServiceBillDTOOptional.map(monthlyServiceBillDTO1 -> {
            monthlyServiceBillDTO.setId(monthlyServiceBillDTO1.getId());
            MonthlyServiceBillDTO updatedMonthlyServiceBill = monthlyServiceBillService.save(monthlyServiceBillDTO);
            return new ResponseEntity<>(updatedMonthlyServiceBill,HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MonthlyServiceBillDTO> deleteMonthlyServiceBill(@PathVariable Integer id) {
        // Lấy thử đối tượng có id đó ra xem tồn tại chưa để xóa, ko thì trả về status not found
        Optional<MonthlyServiceBillDTO> monthlyServiceBillDTOOptional = monthlyServiceBillService.findById(id);
        return monthlyServiceBillDTOOptional.map(monthlyServiceBillDTO -> {
            monthlyServiceBillService.remove(id);
            return new ResponseEntity<>(monthlyServiceBillDTO, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/find-all-by-company-and-month")
    public ResponseEntity<List<MonthlyServiceBillDTO>> findAllByCompanyIdAndMonthId(@RequestParam Integer companyId,
                                                                              @RequestParam Integer monthId){
        List<MonthlyServiceBillDTO> result =
                monthlyServiceBillService.findMonthlyServiceBillsOfCompanyInAMonth(companyId, monthId);

        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
