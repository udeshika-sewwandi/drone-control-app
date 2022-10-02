package service;

import com.sew.drone.model.Medication;
import com.sew.drone.repository.MedicationRepository;
import com.sew.drone.service.impl.MedicationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MedicationServiceTest {

  @Mock
  private MedicationRepository medicationRepository;

  @InjectMocks
  private MedicationServiceImpl medicationService;

  @BeforeEach
  private void init() {
    medicationService = new MedicationServiceImpl(medicationRepository);
  }

  @Test
  void saveMedicationTest() {
    Medication medication = new Medication("101", "Med1", 10, null);
    Mockito.when(medicationRepository.save(medication)).thenReturn(medication);

    Medication savedMedication = medicationService.saveMedication(medication);

    Assertions.assertEquals("101", savedMedication.getCode());
  }

  @Test
  void findByIdTest() {
    Optional<Medication> medication = Optional.of(new Medication("101", "Med1", 10, null));

    Mockito.when(medicationRepository.findById("101")).thenReturn(medication);

    Medication medication1 = medicationService.findById("101");

    Assertions.assertEquals("101", medication1.getCode());

  }
}
