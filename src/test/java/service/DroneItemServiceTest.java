package service;

import com.sew.drone.model.droneitems.DroneItems;
import com.sew.drone.model.droneitems.DroneItemsPK;
import com.sew.drone.repository.DroneItemsRepository;
import com.sew.drone.service.impl.DroneItemServiceImpl;
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

@ExtendWith(MockitoExtension.class)
public class DroneItemServiceTest {

  @Mock
  private DroneItemsRepository droneItemsRepository;

  @InjectMocks
  private DroneItemServiceImpl droneItemService;

  @BeforeEach
  private void init() {
    droneItemService = new DroneItemServiceImpl(droneItemsRepository);
  }

  @Test
  void saveDroneItemTest() {
    DroneItems droneItem = new DroneItems(new DroneItemsPK("1001", "101"), 10);

    Mockito.when(droneItemsRepository.save(droneItem)).thenReturn(droneItem);

    DroneItems droneItem1 = droneItemService.saveDroneItem(droneItem);

    Assertions.assertEquals("1001", droneItem1.getId().getSerialNumber());
  }

  @Test
  void findItemsByIdTest() {
    DroneItems droneItem = new DroneItems(new DroneItemsPK("1001", "101"), 10);
    DroneItems droneItem1 = new DroneItems(new DroneItemsPK("1001", "102"), 10);

    List<DroneItems> droneItems = Arrays.asList(droneItem, droneItem1);

    Mockito.when(droneItemService.findItemsById("1001")).thenReturn(droneItems);

    List<DroneItems> droneItems1 = droneItemService.findItemsById("1001");

    Assertions.assertEquals(2, droneItems1.size());
  }
}
