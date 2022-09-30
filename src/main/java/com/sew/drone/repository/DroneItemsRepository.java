package com.sew.drone.repository;

import com.sew.drone.model.droneitems.DroneItems;
import com.sew.drone.model.droneitems.DroneItemsPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DroneItemsRepository extends JpaRepository<DroneItems, DroneItemsPK> {

  List<DroneItems> findById_SerialNumber(String serialNumber);
}
