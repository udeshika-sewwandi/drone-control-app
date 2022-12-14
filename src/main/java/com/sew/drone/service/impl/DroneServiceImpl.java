package com.sew.drone.service.impl;

import com.sew.drone.model.Drone;
import com.sew.drone.repository.DroneRepository;
import com.sew.drone.service.DroneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DroneServiceImpl implements DroneService {

  private static final Logger logger = LoggerFactory.getLogger(DroneServiceImpl.class);

  @Autowired
  private DroneRepository droneRepository;

  public DroneServiceImpl(DroneRepository droneRepository) {
    this.droneRepository = droneRepository;
  }

  @Override
  public Drone saveDrone(Drone drone) {
    logger.info("Saving drone to drone table");

    Drone savedDrone = droneRepository.save(drone);

    logger.info("Drone object was successfully saved");
    return savedDrone;
  }

  @Override
  public double findBatteryCapacityById(String id) {
    logger.info("Fetching battery capacity from drone table for drone id {}", id);

    Optional<Drone> drone = droneRepository.findById(id);

    return drone.map(Drone::getBatteryCapacity).orElse(-1.0);
  }

  @Override
  public List<Drone> findAvailableDrones() {
    logger.info("Fetching available drones from drones table");

    return droneRepository.findByState("IDLE");
  }

  @Override
  public Drone findById(String id) {
    logger.info("Fetching drone for the given id {}", id);

    Optional<Drone> drone = droneRepository.findById(id);

    return drone.orElse(new Drone());
  }

  @Override
  public long getNumberOfSavedDrones() {
    logger.info("Finding the maximum number of saved drones");

    return droneRepository.count();
  }

  @Override
  public List<Drone> findAllDrones() {
    logger.info("Finding all drones in the database");

    return droneRepository.findAll();
  }
}
