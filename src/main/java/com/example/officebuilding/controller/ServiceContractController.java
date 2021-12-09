package com.example.officebuilding.controller;

import com.example.officebuilding.dao.ServiceContractDAO;
import com.example.officebuilding.dtos.MessageDTO;
import com.example.officebuilding.dtos.ServiceContractDTO;
import com.example.officebuilding.service.service_contract.IServiceContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/service-registrations", produces = "application/json")
public class ServiceContractController {
    @Autowired
    private IServiceContractService serviceContractService;

    @PostMapping
    public ResponseEntity<ServiceContractDTO> createNewServiceContract(@RequestBody ServiceContractDTO serviceContractDTO){
        return new ResponseEntity<>(serviceContractService.save(serviceContractDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ServiceContractDTO>> getAllServiceContracts() {
        return new ResponseEntity<>(serviceContractService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceContractDTO> updateServiceContract(@PathVariable Integer id,@RequestBody ServiceContractDTO serviceContractDTO){
        // Lấy thử đối tượng có id đó ra xem tồn tại chưa để cập nhật, ko thì trả về status not found
        Optional<ServiceContractDTO> serviceContractDTOOptional = serviceContractService.findById(id);

        return serviceContractDTOOptional.map(serviceContractDTO1 -> {
            serviceContractDTO.setId(serviceContractDTO1.getId());
            ServiceContractDTO updatedServiceContract = serviceContractService.save(serviceContractDTO);
            return new ResponseEntity<>(updatedServiceContract,HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceContractDTO> deleteServiceContract(@PathVariable Integer id) {
        // Lấy thử đối tượng có id đó ra xem tồn tại chưa để xóa, ko thì trả về status not found
        Optional<ServiceContractDTO> serviceContractDTOOptional = serviceContractService.findById(id);
        return serviceContractDTOOptional.map(serviceContractDTO -> {
            serviceContractService.remove(id);
            return new ResponseEntity<>(serviceContractDTO, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/companyId={id}")
    public ResponseEntity<List<ServiceContractDTO>> getAllServiceContractsByCompany(@PathVariable Integer id) {
        return new ResponseEntity<>(serviceContractService.findAllServiceContractOfCompany(id), HttpStatus.OK);
    }

    @PostMapping("/register-new-service")
    public ResponseEntity<MessageDTO> createNewServiceContract(@RequestParam Integer companyId,
                                                                       @RequestParam Integer serviceId,
                                                                       @RequestBody ServiceContractDTO serviceContractDTO){
        Optional<ServiceContractDTO> savedServiceContract = serviceContractService.createServiceContract(companyId, serviceId, serviceContractDTO);

        if(savedServiceContract.isPresent()){
            return new ResponseEntity<>(new MessageDTO("Bạn đã đăng ký thành công dịch vụ cho công ty!"),HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageDTO("Công ty đã đăng ký dịch vụ này rồi!"),HttpStatus.EXPECTATION_FAILED);
    }

    @GetMapping("/service-name={serviceName}")
    public ResponseEntity<List<ServiceContractDTO>> getServiceContractsByServiceName(@PathVariable String serviceName){
        return new ResponseEntity<>( serviceContractService.findServiceContractByServiceName(serviceName),HttpStatus.OK);
    }
}
