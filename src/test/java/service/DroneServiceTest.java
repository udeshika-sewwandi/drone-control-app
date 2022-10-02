package service;

import com.sew.drone.model.Drone;
import com.sew.drone.repository.DroneRepository;
import com.sew.drone.service.impl.DroneServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DroneServiceTest {

  @Mock
  DroneRepository droneRepository;

  @InjectMocks
  DroneServiceImpl droneService;

  @BeforeEach
  public void init() {
    droneService = new DroneServiceImpl(droneRepository);
  }

  @Test
  void saveDroneTest() {
    Drone drone = new Drone("1001", "Lightweight", 100, 50.3, "IDLE");
    Mockito.when(droneRepository.save(drone)).thenReturn(drone);

    Drone savedDrone = droneService.saveDrone(drone);

    Assertions.assertEquals("1001", savedDrone.getSerialNumber());
  }

  @Test
  void findBatteryCapacityByIdTest() {
    Optional<Drone> drone = Optional.of(new Drone("1001", "Lightweight", 100, 50.3, "IDLE"));

    Mockito.when(droneRepository.findById("1001")).thenReturn(drone);

    Assertions.assertEquals(50.3, droneService.findBatteryCapacityById("1001"));
  }

  @Test
  void findAvailableDronesTest() {
    Drone drone = new Drone("1001", "Lightweight", 100, 50.3, "IDLE");
    Drone drone1 = new Drone("1002", "Heavyweight", 300, 80, "IDLE");

    List<Drone> drones = Arrays.asList(drone, drone1);
    Mockito.when(droneRepository.findByState("IDLE")).thenReturn(drones);

    List<Drone> droneList = droneService.findAvailableDrones();

    Assertions.assertEquals(2, droneList.size());
  }

  @Test
  void findByIdTest() {
    Optional<Drone> drone = Optional.of(new Drone("1001", "Lightweight", 100, 50.3, "IDLE"));
    Mockito.when(droneRepository.findById("1001")).thenReturn(drone);

    Drone savedDrone = droneService.findById("1001");

    Assertions.assertEquals("1001", savedDrone.getSerialNumber());
  }

  @Test
  void getNumberOfSavedDronesTest() {
    Mockito.when(droneRepository.count()).thenReturn(8L);

    Assertions.assertEquals(8, droneService.getNumberOfSavedDrones());
  }

  @Test
  void findAllDronesTest() {
    Drone drone = new Drone("1001", "Lightweight", 100, 50.3, "IDLE");
    Drone drone1 = new Drone("1002", "Heavyweight", 300, 80, "IDLE");

    List<Drone> drones = Arrays.asList(drone, drone1);

    Mockito.when(droneRepository.findAll()).thenReturn(drones);

    List<Drone> drones1 = droneService.findAllDrones();

    Assertions.assertEquals(2, drones1.size());
  }
}
