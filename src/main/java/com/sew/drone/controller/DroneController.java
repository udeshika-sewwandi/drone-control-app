package com.sew.drone.controller;

import com.sew.drone.dto.DroneDto;
import com.sew.drone.error.BadRequestException;
import com.sew.drone.model.Drone;
import com.sew.drone.service.DroneService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/drone")
public class DroneController {

  @Autowired
  private DroneService droneService;

  @Autowired
  private ModelMapper modelMapper;

  @PostMapping
  public ResponseEntity<DroneDto> saveDrone(@RequestBody DroneDto droneDto) {
    if(droneDto == null) {
      throw new BadRequestException("Drone object is null");
    }

    Drone drone = modelMapper.map(droneDto, Drone.class);
    Drone drone1 = droneService.saveDrone(drone);

    if(drone1 != null) {
      return new ResponseEntity<>(modelMapper.map(drone1, DroneDto.class), HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(new DroneDto(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/battery-capacity/{id}")
  public ResponseEntity<Double> findBatteryCapacity(@PathVariable String id) {
    if(id.isBlank())       {
      throw new BadRequestException("The given drone id is null or empty");
    }

    return new ResponseEntity<>(droneService.findBatteryCapacityById(id), HttpStatus.OK);

  }
}
