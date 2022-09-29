package com.sew.drone.service.impl;

import com.sew.drone.model.Medication;
import com.sew.drone.repository.MedicationRepository;
import com.sew.drone.service.MedicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicationServiceImpl implements MedicationService {

  private static final Logger logger = LoggerFactory.getLogger(MedicationServiceImpl.class);

  @Autowired
  private MedicationRepository medicationRepository;

  @Override
  public Medication saveMedication(Medication medication) {
    logger.info("Saving medication to medication table");

    Medication savedMedication = medicationRepository.save(medication);

    logger.info("Medication object was successfully saved");
    return savedMedication;
  }

  @Override
  public Medication findById(String id) {
    logger.info("Fetching medication for the given id {}", id);

    Optional<Medication> medication = medicationRepository.findById(id);

    return medication.orElse(new Medication());
  }
}
