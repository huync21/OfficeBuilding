package com.example.officebuilding.service.floor;

import com.example.officebuilding.entities.FloorEntity;
import com.example.officebuilding.repository.floor.IFloorEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FloorEntityService implements IFloorEntityService {
    @Autowired
    private IFloorEntityRepository floorEntityRepository;

    @Override
    public Iterable<FloorEntity> findAll() {
        return floorEntityRepository.findAll();
    }

    @Override
    public Optional<FloorEntity> findById(Integer id) {
        return floorEntityRepository.findById(id);
    }

    @Override
    public FloorEntity save(FloorEntity floorEntity) {
        return floorEntityRepository.save(floorEntity);
    }

    @Override
    public void remove(Integer id) {
        floorEntityRepository.deleteById(id);
    }
}
