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

  private int weight;

  private int batteryCapacity;

  private String state;
}
