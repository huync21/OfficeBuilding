package com.example.officebuilding.controller;

import com.example.officebuilding.dtos.MonthlySalaryDTO;
import com.example.officebuilding.service.monthly_salary.IMonthlySalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/monthlySalaries", produces = "application/json")
public class MonthlySalaryController {
    @Autowired
    private IMonthlySalaryService monthlySalaryService;

    @PostMapping
    public ResponseEntity<MonthlySalaryDTO> createNewMonthlySalary(@RequestBody MonthlySalaryDTO monthlySalaryDTO){
        return new ResponseEntity<>(monthlySalaryService.save(monthlySalaryDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MonthlySalaryDTO>> getAllMonthlySalarys() {
        return new ResponseEntity<>(monthlySalaryService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MonthlySalaryDTO> updateMonthlySalary(@PathVariable Integer id,@RequestBody MonthlySalaryDTO monthlySalaryDTO){
        // Lấy thử đối tượng có id đó ra xem tồn tại chưa để cập nhật, ko thì trả về status not found
        Optional<MonthlySalaryDTO> monthlySalaryDTOOptional = monthlySalaryService.findById(id);

        return monthlySalaryDTOOptional.map(monthlySalaryDTO1 -> {
            monthlySalaryDTO.setId(monthlySalaryDTO1.getId());
            MonthlySalaryDTO updatedMonthlySalary = monthlySalaryService.save(monthlySalaryDTO);
            return new ResponseEntity<>(updatedMonthlySalary,HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MonthlySalaryDTO> deleteMonthlySalary(@PathVariable Integer id) {
        // Lấy thử đối tượng có id đó ra xem tồn tại chưa để xóa, ko thì trả về status not found
        Optional<MonthlySalaryDTO> monthlySalaryDTOOptional = monthlySalaryService.findById(id);
        return monthlySalaryDTOOptional.map(monthlySalaryDTO -> {
            monthlySalaryService.remove(id);
            return new ResponseEntity<>(monthlySalaryDTO, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/monthId={monthId}")
    public ResponseEntity<List<MonthlySalaryDTO>> getMonthlySalaryByMonthId(@PathVariable Integer monthId){
        return new ResponseEntity<>(monthlySalaryService.getMonthlySalariesByMonthId(monthId),HttpStatus.OK);
    }
}
