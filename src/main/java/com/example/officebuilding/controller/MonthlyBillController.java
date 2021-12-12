package com.example.officebuilding.controller;

import com.example.officebuilding.dtos.MonthlyBillDTO;
import com.example.officebuilding.service.monthly_bill.IMonthlyBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/monthlyBills", produces = "application/json")
public class MonthlyBillController {
    @Autowired
    private IMonthlyBillService monthlyBillService;

    @GetMapping("/get-monthly-bill-by-company-and-month")
    private ResponseEntity<List<MonthlyBillDTO>> getMonthlyBillsByCompanyIdAndMonthId(@RequestParam Integer companyId,
                                                                                      @RequestParam Integer monthId){
        List<MonthlyBillDTO> monthlyBillsOfCompanyInAMonth = monthlyBillService.findMonthlyBillsOfCompanyInAMonth(companyId, monthId);
        return new ResponseEntity<>(monthlyBillsOfCompanyInAMonth,HttpStatus.OK);
    }
}
