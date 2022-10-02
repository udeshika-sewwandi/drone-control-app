package controller;

import com.sew.drone.controller.DroneController;
import com.sew.drone.dto.DroneDto;
import com.sew.drone.error.BadRequestException;
import com.sew.drone.error.DroneNotFoundException;
import com.sew.drone.model.Drone;
import com.sew.drone.service.DroneService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class DroneControllerTest {

  @Mock
  private DroneService droneService;

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private DroneController droneController;

  @BeforeEach
  public void init() {
    droneController = new DroneController(droneService, modelMapper);
  }

  @Test
  void saveDroneTest() {
    // Test expected behavior
    DroneDto droneDto = new DroneDto("1001", "Lightweight", 100, 50.3, "IDLE");
    Drone drone = new Drone("1001", "Lightweight", 100, 50.3, "IDLE");

    Mockito.when(modelMapper.map(droneDto, Drone.class)).thenReturn(drone);
    Mockito.when(droneService.saveDrone(drone)).thenReturn(drone);
    Mockito.when(modelMapper.map(drone, DroneDto.class)).thenReturn(droneDto);

    DroneDto savedDrone = droneController.saveDrone(droneDto).getBody();

    Assertions.assertEquals("1001", savedDrone.getSerialNumber());
    Assertions.assertEquals(100, savedDrone.getWeight());

    // Test when save drone returns null
    Mockito.when(droneService.saveDrone(drone)).thenReturn(null);

    savedDrone = droneController.saveDrone(droneDto).getBody();

    Assertions.assertNull(savedDrone.getSerialNumber());
  }

  @Test
  void saveDroneTestOnError() {
    Assertions.assertThrows(BadRequestException.class, () -> {
      droneController.saveDrone(null);
    });
  }

  @Test
  void updateDroneStatusTest() {
    DroneDto droneDto = new DroneDto("1001", "Lightweight", 100, 50.3, "IDLE");
    Drone drone = new Drone("1001", "Lightweight", 100, 50.3, "IDLE");

    Mockito.when(droneService.findById("1001")).thenReturn(drone);

    drone.setState("LOADING");
    droneDto.setState("LOADING");

    Mockito.when(droneService.saveDrone(drone)).thenReturn(drone);
    Mockito.when(modelMapper.map(drone, DroneDto.class)).thenReturn(droneDto);

    DroneDto savedDrone = droneController.updateDroneStatus("1001", "LOADING").getBody();

    Assertions.assertEquals("1001", savedDrone.getSerialNumber());
    Assertions.assertEquals("LOADING", savedDrone.getState());

    // Test when save drone returns null
    Mockito.when(droneService.saveDrone(drone)).thenReturn(null);

    savedDrone = droneController.updateDroneStatus("1001", "LOADING").getBody();

    Assertions.assertNull(savedDrone.getSerialNumber());
  }

  @Test
  void updateDroneStatusTestOnError() {
    Assertions.assertThrows(BadRequestException.class, () -> {
      droneController.updateDroneStatus("", "LOADING");
    });

    Assertions.assertThrows(BadRequestException.class, () -> {
      droneController.updateDroneStatus("1001", "");
    });

    Assertions.assertThrows(BadRequestException.class, () -> {
      droneController.updateDroneStatus("", "");
    });

    Assertions.assertThrows(BadRequestException.class, () -> {
      droneController.updateDroneStatus(null, "");
    });

    Assertions.assertThrows(BadRequestException.class, () -> {
      droneController.updateDroneStatus("1001", null);
    });

    Assertions.assertThrows(BadRequestException.class, () -> {
      droneController.updateDroneStatus(null, null);
    });

    Drone drone = new Drone();
    drone.setSerialNumber(null);
    Mockito.when(droneService.findById("1001")).thenReturn(drone);

    Assertions.assertThrows(DroneNotFoundException.class, () -> {
      droneController.updateDroneStatus("1001", "LOADING");
    });
  }

  @Test
  void findBatteryCapacityTest() {
    double batteryCapacity = 10.7;

    Mockito.when(droneService.findBatteryCapacityById("1001")).thenReturn(batteryCapacity);

    Assertions.assertEquals(10.7, droneController.findBatteryCapacity("1001").getBody());
  }

  @Test
  void findBatteryCapacityTestOnError() {
    Assertions.assertThrows(BadRequestException.class, () -> {
      droneController.findBatteryCapacity("");
    });
  }

  @Test
  void findAvailableDronesTest() {
    Drone drone = new Drone("1001", "Lightweight", 100, 50.3, "IDLE");
    Drone drone1 = new Drone("1002", "Heavyweight", 150, 50.3, "IDLE");
    Drone drone2 = new Drone("1003", "Lightweight", 300, 50.3, "IDLE");

    List<Drone> drones = new ArrayList<>(Arrays.asList(drone, drone1, drone2));

    DroneDto droneDto = new DroneDto("1001", "Lightweight", 100, 50.3, "IDLE");
    DroneDto droneDto1 = new DroneDto("1002", "Heavyweight", 150, 50.3, "IDLE");
    DroneDto droneDto2 = new DroneDto("1003", "Lightweight", 300, 50.3, "IDLE");

    List<DroneDto> droneDtos = new ArrayList<>(Arrays.asList(droneDto, droneDto1, droneDto2));

    Mockito.when(droneService.findAvailableDrones()).thenReturn(drones);
    Mockito.when(modelMapper.map(drones, new TypeToken<List<DroneDto>>(){}.getType())).thenReturn(droneDtos);

    List<DroneDto> drones1 = droneController.findAvailableDrones().getBody();

    Assertions.assertEquals(3, drones1.size());
  }
}
