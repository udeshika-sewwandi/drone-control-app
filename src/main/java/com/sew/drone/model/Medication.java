package com.sew.drone.model;

import com.sew.drone.dto.AuditDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Medication")
public class Medication extends Audit {

  @Id
  private String code;

  private String name;

  private int weight;

  @Lob
  private byte[] image;
}