package com.sew.drone.service;

import com.sew.drone.model.DroneItems;

public interface DroneItemService {

  /**
   * Save the given drone items.
   * @param droneItem Drone items
   * @return Saved drone items
   */
  DroneItems saveDroneItem(DroneItems droneItem);
}
