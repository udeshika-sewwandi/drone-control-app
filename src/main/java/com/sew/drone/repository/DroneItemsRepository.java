package com.sew.drone.repository;

import com.sew.drone.model.DroneItems;
import com.sew.drone.model.DroneItemsPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneItemsRepository extends JpaRepository<DroneItems, DroneItemsPK> {
}
