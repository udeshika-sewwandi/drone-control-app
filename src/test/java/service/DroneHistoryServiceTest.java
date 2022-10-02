package service;

import com.sew.drone.model.dronehistory.DroneHistory;
import com.sew.drone.model.dronehistory.DroneHistoryPK;
import com.sew.drone.repository.DroneHistoryRepository;
import com.sew.drone.service.impl.DroneHistoryServiceImpl;
import com.sew.drone.service.impl.DroneItemServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DroneHistoryServiceTest {

  @Mock
  private DroneHistoryRepository droneHistoryRepository;

  @InjectMocks
  private DroneHistoryServiceImpl droneHistoryService;

  @BeforeEach
  public void init() {
    droneHistoryService = new DroneHistoryServiceImpl(droneHistoryRepository);
  }

  @Test
  void saveDroneHistoryTest() {
    DroneHistory droneHistory = new DroneHistory(new DroneHistoryPK("1001", 40.5));

    Mockito.when(droneHistoryRepository.save(droneHistory)).thenReturn(droneHistory);

    DroneHistory savedDroneHistory = droneHistoryService.saveDroneHistory(droneHistory);

    Assertions.assertEquals("1001", savedDroneHistory.getId().getSerialNumber());
  }
}
