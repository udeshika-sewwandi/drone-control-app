package controller;

import com.sew.drone.controller.MedicationController;
import com.sew.drone.dto.MedicationDto;
import com.sew.drone.error.BadRequestException;
import com.sew.drone.model.Medication;
import com.sew.drone.service.ImageService;
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

@ExtendWith(MockitoExtension.class)
public class MedicationControllerTest {

  @Mock
  private MedicationService medicationService;

  @Mock
  private ImageService imageService;

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private MedicationController medicationController;

  @BeforeEach
  private void init() {
    medicationController = new MedicationController(medicationService, imageService, modelMapper);
  }

  @Test
  void saveMedicationTest() {
    MedicationDto medicationDto = new MedicationDto("101", "Med1", 10, null);
    Medication medication = new Medication("101", "Med1", 10, null);

    Mockito.when(modelMapper.map(medicationDto, Medication.class)).thenReturn(medication);
    Mockito.when(medicationService.saveMedication(medication)).thenReturn(medication);
    Mockito.when(modelMapper.map(medication, MedicationDto.class)).thenReturn(medicationDto);

    MedicationDto medicationDto1 = medicationController.saveMedication(medicationDto).getBody();

    Assertions.assertEquals("101", medicationDto1.getCode());

    Mockito.when(medicationService.saveMedication(medication)).thenReturn(null);

    medicationDto1 = medicationController.saveMedication(medicationDto).getBody();

    Assertions.assertEquals(null, medicationDto1.getCode());
  }

  @Test
  void saveMedicationTestOnError() {
    // Medication is null
    Assertions.assertThrows(BadRequestException.class, () -> {
      medicationController.saveMedication(null);
    });

    // Medication code is invalid
    MedicationDto medicationDto = new MedicationDto("&*1med", "Med1", 10, null);
    Medication medication = new Medication("&*1med", "Med1", 10, null);

    Assertions.assertThrows(BadRequestException.class, () -> {
      medicationController.saveMedication(medicationDto);
    });

    // Medication name is invalid
    MedicationDto medicationDto1 = new MedicationDto("101", "&*", 10, null);

    Assertions.assertThrows(BadRequestException.class, () -> {
      medicationController.saveMedication(medicationDto1);
    });
  }
}
