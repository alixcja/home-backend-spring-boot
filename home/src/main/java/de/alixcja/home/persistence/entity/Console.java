package de.alixcja.home.persistence.entity;

import jakarta.persistence.Entity;

@Entity
public class Console extends BookingEntity {

  public Console(String name, String description) {
    super(name, description, BookingEntityType.CONSOLE);
  }
}
