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
@RequestMapping("/api/drone/items")
public class DroneItemsController {
  private static final Logger logger = LoggerFactory.getLogger(DroneController.class);
  private static final Type DRONE_ITEMS_DTO_LIST_TYPE = new TypeToken<List<DroneItemsDto>>(){}.getType();

  @Autowired
  private DroneService droneService;

  @Autowired
  private DroneItemService droneItemService;

  @Autowired
  private MedicationService medicationService;

  @Autowired
  private ModelMapper modelMapper;

  @PostMapping
  public ResponseEntity<DroneItemsDto> loadItemsToDrone(@RequestBody DroneItemsDto dto) {
    if(dto == null) {
      throw new BadRequestException("Drone item is null");
    }

    DroneItems droneItem = modelMapper.map(dto, DroneItems.class);

    Drone drone = droneService.findById(dto.getId().getSerialNumber());

    if(!"IDLE".equals(drone.getState())) {
      throw new DroneException("Drone is not in IDLE state, cannot load");
    }
    if(drone.getBatteryCapacity() < 25) {
      throw new DroneException("Battery percentage of the drone is less than 25%");
    }

    Medication medication = medicationService.findById(dto.getId().getCode());
    List<DroneItems> droneItems = droneItemService.findItemsById(dto.getId().getSerialNumber());

    // Get weight of all loaded items in the drone
    double totalAddedWeight = medication.getWeight() * droneItem.getQuantity();

    for (DroneItems di: droneItems
         ) {
      Medication medication1 = medicationService.findById(dto.getId().getCode());
      totalAddedWeight += di.getQuantity() * medication1.getWeight();
    }

    if((drone.getWeight() < totalAddedWeight)) {
      throw new DroneException("Drone is overloaded with the new item. So cannot load " + dto.getId().getCode());
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

  @GetMapping("/{id}")
  public ResponseEntity<List<DroneItemsDto>> getDroneItemsByDroneId(@PathVariable String id) {
    if(id.isBlank()) {
      throw new BadRequestException("Drone id is null");
    }

    List<DroneItems> droneItems = droneItemService.findItemsById(id);

    if(droneItems != null) {
      List<DroneItemsDto> droneItemsDtos = modelMapper.map(droneItems, DRONE_ITEMS_DTO_LIST_TYPE);
      return new ResponseEntity<>(droneItemsDtos, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
