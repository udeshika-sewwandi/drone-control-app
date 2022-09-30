package com.sew.drone.model.dronehistory;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class DroneHistoryPK implements Serializable {

  private static final long serialVersionUID = 94894820394032L;

  @Column(name = "serial_number")
  private String serialNumber;

  @Column(name = "battery_capacity")
  private double batteryCapacity;
}
