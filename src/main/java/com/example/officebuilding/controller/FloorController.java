package com.example.officebuilding.controller;

import com.example.officebuilding.entities.FloorEntity;
import com.example.officebuilding.service.IFloorEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/floors", produces = "application/json")
public class FloorController {
    @Autowired
    private IFloorEntityService floorEntityService;

    @PostMapping
    public ResponseEntity<FloorEntity> createNewFloor(@RequestBody FloorEntity floorEntity){
        return new ResponseEntity<>(floorEntityService.save(floorEntity), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<FloorEntity>> getAllFloors() {
        return new ResponseEntity<>(floorEntityService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FloorEntity> updateFloor(@PathVariable Integer id,@RequestBody FloorEntity floorEntity){
        Optional<FloorEntity> floorEntityOptional = floorEntityService.findById(id);

        return floorEntityOptional.map(floorEntity1 -> {
            floorEntity.setId(floorEntity1.getId());
            return new ResponseEntity<>(floorEntityService.save(floorEntity),HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FloorEntity> deleteCategory(@PathVariable Integer id) {
        Optional<FloorEntity> floorEntityOptional = floorEntityService.findById(id);
        return floorEntityOptional.map(floorEntity -> {
            floorEntityService.remove(id);
            return new ResponseEntity<>(floorEntity, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
