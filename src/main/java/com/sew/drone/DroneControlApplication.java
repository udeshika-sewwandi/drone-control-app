package com.sew.drone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DroneControlApplication {
  public static void main(String[] args) {
    SpringApplication.run(DroneControlApplication.class, args);
  }
}


