package com.example.officebuilding.service.floor;

import com.example.officebuilding.dtos.FloorDTO;
import com.example.officebuilding.service.IGeneralService;

public interface IFloorService extends IGeneralService<FloorDTO> {
    double getTheRestAreaOfFloor(Integer floorId);
}
