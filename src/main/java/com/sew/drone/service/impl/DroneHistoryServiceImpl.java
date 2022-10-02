package com.sew.drone.service.impl;

import com.sew.drone.model.dronehistory.DroneHistory;
import com.sew.drone.repository.DroneHistoryRepository;
import com.sew.drone.service.DroneHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DroneHistoryServiceImpl implements DroneHistoryService {

  private static final Logger logger = LoggerFactory.getLogger(DroneHistoryServiceImpl.class);

  @Autowired
  private DroneHistoryRepository droneHistoryRepository;

  public DroneHistoryServiceImpl(DroneHistoryRepository droneHistoryRepository) {
    this.droneHistoryRepository = droneHistoryRepository;
  }

  @Override
  public DroneHistory saveDroneHistory(DroneHistory droneHistory) {
    logger.info("Saving drone history");

    return droneHistoryRepository.save(droneHistory);
  }
}
