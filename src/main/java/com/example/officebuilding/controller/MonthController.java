package com.example.officebuilding.controller;

import com.example.officebuilding.dtos.MonthDTO;
import com.example.officebuilding.service.month.IMonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/months", produces = "application/json")
public class MonthController {
    @Autowired
    private IMonthService monthService;

    @PostMapping
    public ResponseEntity<MonthDTO> createNewMonth(@RequestBody MonthDTO monthDTO){
        return new ResponseEntity<>(monthService.save(monthDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MonthDTO>> getAllMonths() {
        return new ResponseEntity<>(monthService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MonthDTO> updateMonth(@PathVariable Integer id, @RequestBody MonthDTO monthDTO){
        // Lấy thử đối tượng có id đó ra xem tồn tại chưa để cập nhật, ko thì trả về status not found
        Optional<MonthDTO> monthDTOOptional = monthService.findById(id);

        return monthDTOOptional.map(monthDTO1 -> {
            monthDTO.setId(monthDTO1.getId());
            MonthDTO updatedMonth = monthService.save(monthDTO);
            return new ResponseEntity<>(updatedMonth,HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MonthDTO> deleteMonth(@PathVariable Integer id) {
        // Lấy thử đối tượng có id đó ra xem tồn tại chưa để xóa, ko thì trả về status not found
        Optional<MonthDTO> monthDTOOptional = monthService.findById(id);
        return monthDTOOptional.map(monthDTO -> {
            monthService.remove(id);
            return new ResponseEntity<>(monthDTO, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
