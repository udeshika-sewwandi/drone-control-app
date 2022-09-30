package com.sew.drone.service;

import com.sew.drone.model.DroneItems;
import com.sew.drone.model.DroneItemsPK;

import java.util.List;

public interface DroneItemService {

  /**
   * Save the given drone items.
   * @param droneItem Drone items
   * @return Saved drone items
   */
  DroneItems saveDroneItem(DroneItems droneItem);

  /**
   * Find loaded items by id
   * @param id Drone item id
   * @return A list of loaded items for the given id
   */
  List<DroneItems> findItemsById(String id);
}
