package com.sew.drone.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicationDto extends AuditDto {

  private String code;

  private String name;

  private int weight;

  @JsonIgnore
  private byte[] image;
}
