package com.example.officebuilding.controller;

import com.example.officebuilding.dtos.ServiceDTO;
import com.example.officebuilding.service.service.IServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/services", produces = "application/json")
public class ServiceController {
    @Autowired
    private IServiceService serviceService;

    @PostMapping
    public ResponseEntity<ServiceDTO> createNewService(@RequestBody ServiceDTO serviceDTO){
        return new ResponseEntity<>(serviceService.save(serviceDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ServiceDTO>> getAllServices() {
        return new ResponseEntity<>(serviceService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceDTO> updateService(@PathVariable Integer id,@RequestBody ServiceDTO serviceDTO){
        // Lấy thử đối tượng có id đó ra xem tồn tại chưa để cập nhật, ko thì trả về status not found
        Optional<ServiceDTO> serviceDTOOptional = serviceService.findById(id);

        return serviceDTOOptional.map(serviceDTO1 -> {
            serviceDTO.setId(serviceDTO1.getId());
            ServiceDTO updatedService = serviceService.save(serviceDTO);
            return new ResponseEntity<>(updatedService,HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceDTO> deleteService(@PathVariable Integer id) {
        // Lấy thử đối tượng có id đó ra xem tồn tại chưa để xóa, ko thì trả về status not found
        Optional<ServiceDTO> serviceDTOOptional = serviceService.findById(id);
        return serviceDTOOptional.map(serviceDTO -> {
            serviceService.remove(id);
            return new ResponseEntity<>(serviceDTO, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}