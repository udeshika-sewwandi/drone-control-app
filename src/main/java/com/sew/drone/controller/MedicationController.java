package com.sew.drone.controller;

import com.sew.drone.dto.MedicationDto;
import com.sew.drone.error.BadRequestException;
import com.sew.drone.error.MedicationNotFound;
import com.sew.drone.model.Medication;
import com.sew.drone.service.ImageService;
import com.sew.drone.service.MedicationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/medication")
public class MedicationController {

  @Autowired
  private MedicationService medicationService;

  @Autowired
  private ImageService imageService;

  @Autowired
  private ModelMapper modelMapper;

  @PostMapping
  public ResponseEntity<MedicationDto> saveMedication(@RequestBody MedicationDto medicationDto) {
    if(medicationDto == null) {
      throw new BadRequestException("Medication object is null");
    }

    // Check if name contains only letters, numbers, hyphen and underscore
    Pattern namePattern = Pattern.compile("[a-zA-Z0-9-_]*");

    if(!namePattern.matcher(medicationDto.getName()).matches()) {
      throw new BadRequestException("Medication name should contain only letters, numbers, hyphen and underscore");
    }

    // Check if code contains only upper case letters, numbers and underscore
    Pattern codePattern = Pattern.compile("[A-Z0-9_]*");

    if(!codePattern.matcher(medicationDto.getCode()).matches()) {
      throw new BadRequestException("Medication code should contain only upper case letters, numbers and underscore");
    }

    Medication medication = modelMapper.map(medicationDto, Medication.class);
    Medication medication1 = medicationService.saveMedication(medication);

    if(medication1!= null) {
      return new ResponseEntity<>(modelMapper.map(medication1, MedicationDto.class), HttpStatus.CREATED);
    } else {
      return new ResponseEntity<>(new MedicationDto(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/image/{id}")
  public ResponseEntity<MedicationDto> updateMedicationImage(@PathVariable String id,
      @RequestParam("image") MultipartFile image) {
    if(image == null) {
      throw new BadRequestException("Image is null");
    }
    Medication medication = medicationService.findById(id);

    if(medication.getCode() == null) {
      throw new MedicationNotFound("Medication not found for the given id " + id);
    }

    //Converts image into byte array
    byte[] imageBytes = imageService.convertImageToByteArray(image);
    if(imageBytes.length != 0) {
      medication.setImage(imageBytes);

      Medication medication1 = medicationService.saveMedication(medication);

      if(medication1!= null) {
        return new ResponseEntity<>(modelMapper.map(medication1, MedicationDto.class), HttpStatus.OK);
      }
    }
    return new ResponseEntity<>(new MedicationDto(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
