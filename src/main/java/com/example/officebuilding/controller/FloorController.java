package com.example.officebuilding.controller;

import com.example.officebuilding.entities.FloorEntity;
import com.example.officebuilding.service.IFloorEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
