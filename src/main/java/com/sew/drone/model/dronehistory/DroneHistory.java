package com.sew.drone.model.dronehistory;

import com.sew.drone.model.Audit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "drone_history")
public class DroneHistory extends Audit {

  @EmbeddedId
  private DroneHistoryPK id;

}
