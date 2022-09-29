package com.sew.drone.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DroneException extends RuntimeException {
  private String message;

  public DroneException(String message) {
    super(message);
  }
}
