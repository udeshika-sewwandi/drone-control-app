package com.sew.drone.service;

import com.sew.drone.model.Drone;

import java.util.List;

public interface DroneService {

  /**
   * Save te given drone.
   * @param drone Drone object
   * @return Saved drone object
   */
  Drone saveDrone(Drone drone);

  /**
   * Find the battery capacity of a drone specified by the given id.
   * @param id Drone id
   * @return The battery capacity of a drone specified by the given id
   */
  double findBatteryCapacityById(String id);

  /**
   * Find available drones.
   *
   * @return A list of available drones
   */
  List<Drone> findAvailableDrones();

  /**
   * Find drone for the specified id.
   * @param id Drone id
   * @return A drone specified by the given id
   */
  Drone findById(String id);
}
