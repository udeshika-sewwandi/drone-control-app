package com.sew.drone.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MedicationNotFound extends RuntimeException {

  private String message;

  public MedicationNotFound(String message) {
    super(message);
  }
}
