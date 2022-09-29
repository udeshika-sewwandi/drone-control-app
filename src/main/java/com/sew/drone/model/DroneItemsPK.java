package com.sew.drone.model;

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
public class DroneItemsPK implements Serializable {

  private static final long serialVersionUID = 9794804950439L;

  @Column(name = "serial_number")
  private String serialNumber;

  private String code;
}
