package com.sew.drone.service;

import com.sew.drone.model.Drone;

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
}
