package com.example.officebuilding.controller;

import com.example.officebuilding.dtos.BuildingEmployeeDTO;
import com.example.officebuilding.dtos.CompanyDTO;
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

    @GetMapping("/{id}")
    public ResponseEntity<ServiceDTO> findServiceById(@PathVariable Integer id){
        Optional<ServiceDTO> service = serviceService.findById(id);
        return service.map(serviceDTO -> {
            return new ResponseEntity<>(serviceDTO,HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
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

    // Lấy ra những dịch vụ mà công ty chưa đăng ký để đăng ký
    @GetMapping("/unregisterd/companyId={companyId}")
    public ResponseEntity<List<ServiceDTO>> findAllUnregisteredServiceOfCompany(@RequestParam Integer companyId){
        List<ServiceDTO> allUnregisterdServices = serviceService.findAllUnregisterdServices(companyId);
        return new ResponseEntity<>(allUnregisterdServices,HttpStatus.OK);
    }

    // Lấy ra những dịch vụ mà công ty chưa đăng ký theo tên dịch vụ
    @GetMapping("/unregisterd")
    public ResponseEntity<List<ServiceDTO>> findAllUnregisteredServiceByCompanyAndServiceName(@RequestParam Integer companyId,@RequestParam String serviceName){
        List<ServiceDTO> allUnregisterdServices = serviceService.findAllUnregisterdServicesByServiceName(companyId,serviceName);
        if(allUnregisterdServices.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allUnregisterdServices,HttpStatus.OK);

    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<ServiceDTO>> findServicesByName(@PathVariable String name){
        List<ServiceDTO> services = serviceService.findServicesByName(name);
        if(services.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(services,HttpStatus.OK);
    }

}
