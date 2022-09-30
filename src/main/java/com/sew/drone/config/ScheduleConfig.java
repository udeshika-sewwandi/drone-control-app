package com.sew.drone.config;

import com.sew.drone.model.Drone;
import com.sew.drone.model.dronehistory.DroneHistory;
import com.sew.drone.model.dronehistory.DroneHistoryPK;
import com.sew.drone.service.DroneHistoryService;
import com.sew.drone.service.DroneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
public class ScheduleConfig {

  private static final Logger logger = LoggerFactory.getLogger(ScheduleConfig.class);

  @Autowired
  private DroneService droneService;
  @Autowired
  private DroneHistoryService droneHistoryService;

  @Scheduled(cron = "0 0/5 * * * *")
  public void scheduleBatteryCapacityMonitoringTask() {
    // Get all drones saved in the database
    List<Drone> drones = droneService.findAllDrones();

    // Update history with battery capacity
    drones.forEach(drone -> {
      DroneHistory droneHistory = new DroneHistory();
      droneHistory.setId(new DroneHistoryPK(drone.getSerialNumber(), drone.getBatteryCapacity()));
      droneHistoryService.saveDroneHistory(droneHistory);
    });

  }
}
