package com.sew.drone.repository;

import com.sew.drone.model.dronehistory.DroneHistory;
import com.sew.drone.model.dronehistory.DroneHistoryPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneHistoryRepository extends JpaRepository<DroneHistory, DroneHistoryPK> {
}
