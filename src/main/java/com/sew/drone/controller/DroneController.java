package com.sew.drone.controller;

import com.sew.drone.dto.DroneDto;
import com.sew.drone.dto.DroneItemsDto;
import com.sew.drone.error.BadRequestException;
import com.sew.drone.error.DroneException;
import com.sew.drone.model.Drone;
import com.sew.drone.model.DroneItems;
import com.sew.drone.model.Medication;
import com.sew.drone.service.DroneItemService;
import com.sew.drone.service.DroneService;
import com.sew.drone.service.MedicationService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  private static final Logger logger = LoggerFactory.getLogger(DroneController.class);
  private static final Type DRONE_LIST_TYPE = new TypeToken<List<DroneDto>>(){}.getType();

  @Autowired
  private DroneService droneService;

  @Autowired
  private DroneItemService droneItemService;

  @Autowired
  private MedicationService medicationService;

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

  @PostMapping("/load")
  public ResponseEntity<DroneItemsDto> loadItemsToDrone(@RequestBody DroneItemsDto dto) {
    if(dto == null) {
      throw new BadRequestException("Drone item is null");
    }

    DroneItems droneItem = modelMapper.map(dto, DroneItems.class);

    Drone drone = droneService.findById(dto.getId().getSerialNumber());
    Medication medication = medicationService.findById(dto.getId().getCode());

    if("IDLE".equals(drone.getState())) {
      throw new DroneException("Drone is not in IDLE state, cannot load");
    }
    if((drone.getWeight() < (medication.getWeight() * droneItem.getQuantity()))) {
      throw new DroneException("Drone is overloaded");
    }
    if(drone.getBatteryCapacity() < 25) {
      throw new DroneException("Battery percentage of the drone is less than 25%");
    }

    DroneItems savedDroneItems = droneItemService.saveDroneItem(droneItem);
    // change drone state and save in the database
    drone.setState("LOADING");
    Drone drone1 = droneService.saveDrone(drone);

    logger.info("Saved new drone state {} to the database", drone1.getState());

    if(savedDroneItems != null) {
      return new ResponseEntity<>(modelMapper.map(savedDroneItems, DroneItemsDto.class), HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(new DroneItemsDto(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
