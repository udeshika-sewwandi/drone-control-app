package com.sew.drone.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DroneNotFoundException extends RuntimeException {
  private String message;

  public DroneNotFoundException(String message) {
    super(message);
  }
}
