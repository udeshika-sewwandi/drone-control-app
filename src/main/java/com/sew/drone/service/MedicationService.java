package com.sew.drone.service;

import com.sew.drone.model.Medication;

public interface MedicationService {

  /**
   * Save a given medication.
   *
   * @param medication Medication object
   * @return Saved medication object
   */
  Medication saveMedication(Medication medication);

  /**
   * Find a medication by the given id.
   * @param id Medication id
   * @return A medication by the given id
   */
  Medication findById(String id);
}
