package com.example.officebuilding.controller;

import com.example.officebuilding.dtos.ContractDTO;
import com.example.officebuilding.repository.contract.IContractRepository;
import com.example.officebuilding.service.contract.IContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "api/contracts", produces = "application/json")
public class ContractController {
    @Autowired
    private IContractService contractService;

    @PostMapping
    public ResponseEntity<ContractDTO> createNewContract(@RequestBody ContractDTO contractDTO){
        return new ResponseEntity<>(contractService.save(contractDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ContractDTO>> getAllContracts() {
        return new ResponseEntity<>(contractService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContractDTO> updateContract(@PathVariable Integer id,@RequestBody ContractDTO contractDTO){
        // Lấy thử đối tượng có id đó ra xem tồn tại chưa để cập nhật, ko thì trả về status not found
        Optional<ContractDTO> contractDTOOptional = contractService.findById(id);

        return contractDTOOptional.map(contractDTO1 -> {
            contractDTO.setId(contractDTO1.getId());
            ContractDTO updatedContract = contractService.save(contractDTO);
            return new ResponseEntity<>(updatedContract,HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ContractDTO> deleteCategory(@PathVariable Integer id) {
        // Lấy thử đối tượng có id đó ra xem tồn tại chưa để xóa, ko thì trả về status not found
        Optional<ContractDTO> contractDTOOptional = contractService.findById(id);
        return contractDTOOptional.map(contractDTO -> {
            contractService.remove(id);
            return new ResponseEntity<>(contractDTO, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
