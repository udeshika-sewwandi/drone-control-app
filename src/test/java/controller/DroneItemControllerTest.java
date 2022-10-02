package controller;

import com.sew.drone.controller.DroneItemsController;
import com.sew.drone.dto.DroneItemsDto;
import com.sew.drone.dto.DroneItemsPKDto;
import com.sew.drone.error.BadRequestException;
import com.sew.drone.error.DroneException;
import com.sew.drone.model.Drone;
import com.sew.drone.model.Medication;
import com.sew.drone.model.droneitems.DroneItems;
import com.sew.drone.model.droneitems.DroneItemsPK;
import com.sew.drone.service.DroneItemService;
import com.sew.drone.service.DroneService;
import com.sew.drone.service.MedicationService;
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
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class DroneItemControllerTest {

  @Mock
  private DroneService droneService;

  @Mock
  private ModelMapper modelMapper;

  @Mock
  private DroneItemService droneItemService;

  @Mock
  private MedicationService medicationService;

  @InjectMocks
  private DroneItemsController droneItemsController;

  @BeforeEach
  public void init() {
    droneItemsController = new DroneItemsController(droneService, droneItemService, medicationService, modelMapper);
  }

  @Test
  void loadItemsToDroneTest() {
    // Expected behaviour
    DroneItemsDto dto = new DroneItemsDto(new DroneItemsPKDto("1001", "101"), 10);
    DroneItems droneItem = new DroneItems(new DroneItemsPK("1001", "101"), 10);

    Mockito.when(modelMapper.map(dto, DroneItems.class)).thenReturn(droneItem);

    Drone drone = new Drone("1001", "Lightweight", 200, 50.3, "IDLE");
    Mockito.when(droneService.findById("1001")).thenReturn(drone);

    Medication medication = new Medication();
    medication.setCode("101");
    medication.setName("Med1");
    medication.setWeight(10);

    Mockito.when(medicationService.findById("101")).thenReturn(medication);

    DroneItems droneItem1 = new DroneItems(new DroneItemsPK("1001", "101"), 1);
    DroneItems droneItems2 = new DroneItems(new DroneItemsPK("1001", "102"), 10);

    List<DroneItems> droneItemsDtos = Arrays.asList(droneItem1, droneItems2);
    Mockito.when(droneItemService.findItemsById(dto.getId().getSerialNumber())).thenReturn(droneItemsDtos);

    Medication medication1 = new Medication();
    medication1.setCode("102");
    medication1.setName("Med2");
    medication1.setWeight(1);

    Mockito.when(medicationService.findById("102")).thenReturn(medication1);

    Mockito.when(droneItemService.saveDroneItem(droneItem)).thenReturn(droneItem);

    Drone drone1 = new Drone("1001", "Lightweight", 200, 50.3, "LOADING");
    Mockito.when(droneService.saveDrone(drone)).thenReturn(drone1);
    Mockito.when(modelMapper.map(droneItem, DroneItemsDto.class)).thenReturn(dto);

    DroneItemsDto savedDroneItemsDto = droneItemsController.loadItemsToDrone(dto).getBody();

    Assertions.assertEquals("1001", savedDroneItemsDto.getId().getSerialNumber());
  }

  @Test
  void loadItemsToDroneTestOnError() {
    // When drone is not in idle state
    DroneItemsDto dto = new DroneItemsDto(new DroneItemsPKDto("1001", "101"), 10);
    DroneItems droneItem = new DroneItems(new DroneItemsPK("1001", "101"), 10);

    Mockito.when(modelMapper.map(dto, DroneItems.class)).thenReturn(droneItem);
    Drone drone = new Drone("1001", "Lightweight", 200, 50.3, "LOADING");
    Mockito.when(droneService.findById("1001")).thenReturn(drone);

    Assertions.assertThrows(DroneException.class, () -> {
      droneItemsController.loadItemsToDrone(dto);
    });

    // When battery capacity < 25
    drone = new Drone("1001", "Lightweight", 200, 20, "IDLE");
    Mockito.when(droneService.findById("1001")).thenReturn(drone);
    Assertions.assertThrows(DroneException.class, () -> {
      droneItemsController.loadItemsToDrone(dto);
    });

    // When overloaded
    drone = new Drone("1001", "Lightweight", 200, 50, "IDLE");
    Mockito.when(droneService.findById("1001")).thenReturn(drone);

    Medication medication = new Medication();
    medication.setCode("101");
    medication.setName("Med1");
    medication.setWeight(20);

    Mockito.when(medicationService.findById("101")).thenReturn(medication);

    DroneItems droneItem1 = new DroneItems(new DroneItemsPK("1001", "101"), 10);
    DroneItems droneItems2 = new DroneItems(new DroneItemsPK("1001", "102"), 10);

    List<DroneItems> droneItems = Arrays.asList(droneItem1, droneItems2);
    Mockito.when(droneItemService.findItemsById(dto.getId().getSerialNumber())).thenReturn(droneItems);

    Medication medication1 = new Medication();
    medication1.setCode("102");
    medication1.setName("Med2");
    medication1.setWeight(1);

    Mockito.when(medicationService.findById("102")).thenReturn(medication1);

    Assertions.assertThrows(DroneException.class, () -> {
      droneItemsController.loadItemsToDrone(dto);
    });
  }

  @Test
  void getDroneItemsByDroneIdTest() {
    DroneItems droneItem1 = new DroneItems(new DroneItemsPK("1001", "101"), 10);
    DroneItems droneItems2 = new DroneItems(new DroneItemsPK("1001", "102"), 10);

    List<DroneItems> droneItems = Arrays.asList(droneItem1, droneItems2);
    Mockito.when(droneItemService.findItemsById("1001")).thenReturn(droneItems);

    DroneItemsDto droneItemDto1 = new DroneItemsDto(new DroneItemsPKDto("1001", "101"), 10);
    DroneItemsDto droneItemsDto2 = new DroneItemsDto(new DroneItemsPKDto("1001", "102"), 10);

    List<DroneItemsDto> droneItemsDtos = Arrays.asList(droneItemDto1, droneItemsDto2);

    Mockito.when(modelMapper.map(droneItems, new TypeToken<List<DroneItemsDto>>(){}.getType())).thenReturn(droneItemsDtos);

    List<DroneItemsDto> savedDroneItems = droneItemsController.getDroneItemsByDroneId("1001").getBody();

    Assertions.assertEquals(2, savedDroneItems.size());

    Mockito.when(droneItemService.findItemsById("1001")).thenReturn(null);
    savedDroneItems = droneItemsController.getDroneItemsByDroneId("1001").getBody();

    Assertions.assertEquals(0, savedDroneItems.size());
  }

  @Test
  void getDroneItemsByDroneIdTestOnError() {
    Assertions.assertThrows(BadRequestException.class, () -> {
      droneItemsController.getDroneItemsByDroneId("");
    });
  }
}
