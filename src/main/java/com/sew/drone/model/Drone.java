package com.sew.drone.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Drone")
public class Drone extends Audit {

  @Id
  @Column(name = "serial_number")
  private String serialNumber;

  private String model;

  private double weight;

  @Column(name = "battery_capacity")
  private double batteryCapacity;

  private String state;
}
