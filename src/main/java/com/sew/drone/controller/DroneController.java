package com.sew.drone.controller;

import com.sew.drone.dto.DroneDto;
import com.sew.drone.error.BadRequestException;
import com.sew.drone.error.DroneException;
import com.sew.drone.error.DroneNotFoundException;
import com.sew.drone.model.Drone;
import com.sew.drone.service.DroneService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/drone")
public class DroneController {
  private static final Type DRONE_LIST_TYPE = new TypeToken<List<DroneDto>>(){}.getType();

  @Autowired
  private DroneService droneService;

  @Autowired
  private ModelMapper modelMapper;

  @PostMapping
  public ResponseEntity<DroneDto> saveDrone(@RequestBody DroneDto droneDto) {
    if(droneDto == null) {
      throw new BadRequestException("Drone object is null");
    }
    if(droneService.getNumberOfSavedDrones() == 10) {
      throw  new DroneException("Already 10 drones are added to the fleet");
    }

    Drone drone = modelMapper.map(droneDto, Drone.class);
    Drone drone1 = droneService.saveDrone(drone);

    if(drone1 != null) {
      return new ResponseEntity<>(modelMapper.map(drone1, DroneDto.class), HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(new DroneDto(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/{id}/state/{state}")
  public ResponseEntity<DroneDto> updateDroneStatus(@PathVariable String id, @PathVariable String state) {
    if(state == null) {
      throw new BadRequestException("Drone status is null");
    }

    Drone drone = droneService.findById(id);

    if(drone.getSerialNumber() == null) {
      throw new DroneNotFoundException("No drone found for the given id" + id);
    }

    drone.setState(state);
    Drone savedDrone = droneService.saveDrone(drone);

    if(savedDrone != null) {
      return new ResponseEntity<>(modelMapper.map(savedDrone, DroneDto.class), HttpStatus.OK);
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

  @GetMapping("/available")
  public ResponseEntity<List<DroneDto>> findAvailableDrones() {
    List<Drone> drones = droneService.findAvailableDrones();

    if(drones != null) {
      List<DroneDto> droneDtos = modelMapper.map(drones, DRONE_LIST_TYPE);
      return new ResponseEntity<>(droneDtos, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }
  }
}
