package com.example.officebuilding.controller;

import com.example.officebuilding.dtos.BuildingEmployeeDTO;
import com.example.officebuilding.service.building_employee.IBuildingEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/buildingEmployees", produces = "application/json")
public class BuildingEmployeeController {
    @Autowired
    private IBuildingEmployeeService buildingEmployeeService;

    @PostMapping
    public ResponseEntity<BuildingEmployeeDTO> createNewBuildingEmployee(@RequestBody BuildingEmployeeDTO buildingEmployeeDTO){
        return new ResponseEntity<>(buildingEmployeeService.save(buildingEmployeeDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BuildingEmployeeDTO>> getAllBuildingEmployees() {
        return new ResponseEntity<>(buildingEmployeeService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BuildingEmployeeDTO> updateBuildingEmployee(@PathVariable Integer id, @RequestBody BuildingEmployeeDTO buildingEmployeeDTO){
        // Lấy thử đối tượng có id đó ra xem tồn tại chưa để cập nhật, ko thì trả về status not found
        Optional<BuildingEmployeeDTO> buildingEmployeeDTOOptional = buildingEmployeeService.findById(id);

        return buildingEmployeeDTOOptional.map(buildingEmployeeDTO1 -> {
            buildingEmployeeDTO.setId(buildingEmployeeDTO1.getId());
            BuildingEmployeeDTO updatedBuildingEmployee = buildingEmployeeService.save(buildingEmployeeDTO);
            return new ResponseEntity<>(updatedBuildingEmployee,HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BuildingEmployeeDTO> deleteBuildingEmployee(@PathVariable Integer id) {
        // Lấy thử đối tượng có id đó ra xem tồn tại chưa để xóa, ko thì trả về status not found
        Optional<BuildingEmployeeDTO> buildingEmployeeDTOOptional = buildingEmployeeService.findById(id);
        return buildingEmployeeDTOOptional.map(buildingEmployeeDTO -> {
            buildingEmployeeService.remove(id);
            return new ResponseEntity<>(buildingEmployeeDTO, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
