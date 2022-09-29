package com.sew.drone.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DroneItemsDto {

  private DroneItemsPKDto id;

  private int quantity;
}
