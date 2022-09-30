package com.sew.drone.service;

import com.sew.drone.model.dronehistory.DroneHistory;

public interface DroneHistoryService {

  /**
   * Save the given drone history object.
   *
   * @param droneHistory Drone history
   * @return Saved drone history object
   */
  DroneHistory saveDroneHistory(DroneHistory droneHistory);
}
