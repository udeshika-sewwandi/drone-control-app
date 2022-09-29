package com.sew.drone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DroneDto extends AuditDto {

  private String serialNumber;

  private String model;

  private double weight;

  private double batteryCapacity;

  private String state;
}
