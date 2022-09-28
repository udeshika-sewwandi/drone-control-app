package com.sew.drone.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Audit {

  @Column(name = "created_date", nullable = false,  updatable = false)
  @CreatedDate
  private long createdDate;

  @Column(name = "modified_date")
  @LastModifiedDate
  private long modifiedDate;
}
