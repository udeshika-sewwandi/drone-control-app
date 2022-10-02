package com.sew.drone.service.impl;

import com.sew.drone.model.droneitems.DroneItems;
import com.sew.drone.repository.DroneItemsRepository;
import com.sew.drone.service.DroneItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DroneItemServiceImpl implements DroneItemService {

  private static final Logger logger = LoggerFactory.getLogger(DroneItemServiceImpl.class);

  @Autowired
  private DroneItemsRepository droneItemsRepository;

  public DroneItemServiceImpl(DroneItemsRepository droneItemsRepository) {
    this.droneItemsRepository = droneItemsRepository;
  }

  @Override
  public DroneItems saveDroneItem(DroneItems droneItem) {
    logger.info("Saving drone item to drone_items table");
    return droneItemsRepository.save(droneItem);
  }

  @Override
  public List<DroneItems> findItemsById(String id) {
    logger.info("Fetching data from drone_items table for id {}", id);

    return droneItemsRepository.findById_SerialNumber(id);
  }
}
