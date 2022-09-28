package com.sew.drone.service.impl;

import com.sew.drone.model.Drone;
import com.sew.drone.repository.DroneRepository;
import com.sew.drone.service.DroneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DroneServiceImpl implements DroneService {

  private static final Logger logger = LoggerFactory.getLogger(DroneServiceImpl.class);

  @Autowired
  private DroneRepository droneRepository;

  @Override
  public Drone saveDrone(Drone drone) {
    logger.info("Saving drone to drone table");

    Drone savedDrone = droneRepository.save(drone);

    logger.info("Drone object was successfully saved");
    return savedDrone;
  }
}
