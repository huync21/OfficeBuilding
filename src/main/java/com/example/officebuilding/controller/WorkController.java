package com.example.officebuilding.controller;

import com.example.officebuilding.dtos.SalaryDTO;
import com.example.officebuilding.dtos.WorkDTO;
import com.example.officebuilding.service.work.IWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/work", produces = "application/json")
public class WorkController {
    @Autowired
    private IWorkService workService;

    @PostMapping("/create/empId={id}")
    public ResponseEntity<WorkDTO> createNewWork(@PathVariable Integer id, @RequestBody WorkDTO workDTO){
        workService.createNewWork(id,workDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<WorkDTO>> getAllWork() {
        return new ResponseEntity<>(workService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkDTO> getWorkById(@PathVariable Integer id){
        Optional<WorkDTO> work = workService.findById(id);
        return work.map(workDTO -> {
            return new ResponseEntity<>(workDTO,HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/title={title}")
    public ResponseEntity<List<WorkDTO>> getWorkByTitle(@PathVariable String title){
        List<WorkDTO> workDTOS = workService.findWorkByTitle(title);
        if(workDTOS.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(workDTOS,HttpStatus.OK);
    }

    @GetMapping("/empId={empId}")
    public ResponseEntity<List<WorkDTO>> getWorkByBuildingEmployeeId(@PathVariable Integer empId){
        return new ResponseEntity<>(workService.findWorkByBuildingEmployeeId(empId),HttpStatus.OK);
    }

    @GetMapping("/empId={empId}/title={title}")
    public ResponseEntity<List<WorkDTO>> getWorkByTitleContainingAndBuildingEmployeeId(@PathVariable Integer empId, @PathVariable String title){
        return new ResponseEntity<>(workService.findWorkByTitleContainingAndBuildingEmployeeId(title, empId),HttpStatus.OK);
    }

    @PutMapping("/{workId}/{empId}")
    public ResponseEntity<?> updateWork( @PathVariable Integer empId, @PathVariable Integer workId, @RequestBody WorkDTO workDTO){
        workService.updateWork(workId, empId, workDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WorkDTO> deleteWork(@PathVariable Integer id) {
        // Lấy thử đối tượng có id đó ra xem tồn tại chưa để xóa, ko thì trả về status not found
        Optional<WorkDTO> workDTOOptional = workService.findById(id);
        return workDTOOptional.map(workDTO -> {
            workService.remove(id);
            return new ResponseEntity<>(workDTO, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
