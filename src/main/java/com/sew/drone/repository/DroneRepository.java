package com.sew.drone.repository;

import com.sew.drone.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, String> {

  List<Drone> findByState(String state);
}
