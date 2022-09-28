package com.sew.drone.service;

import com.sew.drone.model.Drone;

public interface DroneService {

  /**
   * Save te given drone.
   * @param drone Drone object
   * @return Saved drone object
   */
  Drone saveDrone(Drone drone);
}
