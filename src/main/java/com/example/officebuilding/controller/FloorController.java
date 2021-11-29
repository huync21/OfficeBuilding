package com.example.officebuilding.controller;

import com.example.officebuilding.dtos.FloorDTO;
import com.example.officebuilding.entities.FloorEntity;
import com.example.officebuilding.service.floor.IFloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/floors", produces = "application/json")
public class FloorController {
    @Autowired
    private IFloorService floorService;

    @PostMapping
    public ResponseEntity<FloorDTO> createNewFloor(@RequestBody FloorDTO floorDTO){
        return new ResponseEntity<>(floorService.save(floorDTO), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<FloorDTO>> getAllFloors() {
        return new ResponseEntity<>(floorService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FloorDTO> updateFloor(@PathVariable Integer id,@RequestBody FloorDTO floorDTO){
        // Lấy thử đối tượng có id đó ra xem tồn tại chưa để cập nhật, ko thì trả về status not found
        Optional<FloorDTO> floorDTOOptional = floorService.findById(id);

        return floorDTOOptional.map(floorDTO1 -> {
            floorDTO.setId(floorDTO1.getId());
            FloorDTO updatedFloor = floorService.save(floorDTO);
            return new ResponseEntity<>(updatedFloor,HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FloorDTO> deleteCategory(@PathVariable Integer id) {
        // Lấy thử đối tượng có id đó ra xem tồn tại chưa để xóa, ko thì trả về status not found
        Optional<FloorDTO> floorDTOOptional = floorService.findById(id);
        return floorDTOOptional.map(floorDTO -> {
            floorService.remove(id);
            return new ResponseEntity<>(floorDTO, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
