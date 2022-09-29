package com.sew.drone.service.impl;

import com.sew.drone.model.DroneItems;
import com.sew.drone.repository.DroneItemsRepository;
import com.sew.drone.service.DroneItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DroneItemServiceImpl implements DroneItemService {

  private static final Logger logger = LoggerFactory.getLogger(DroneItemServiceImpl.class);

  @Autowired
  private DroneItemsRepository droneItemsRepository;

  @Override
  public DroneItems saveDroneItem(DroneItems droneItem) {
    logger.info("Saving drone item to drone_items table");
    return droneItemsRepository.save(droneItem);
  }
}
