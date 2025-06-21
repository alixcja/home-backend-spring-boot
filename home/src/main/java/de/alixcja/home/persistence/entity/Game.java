package de.alixcja.home.persistence.entity;

import jakarta.persistence.Entity;

@Entity
public class Game extends BookingEntity {
  private String consoleType;

  public Game(String name, String description) {
    super(name, description, BookingEntityType.GAME);
  }

  public Game(String name, String description, String consoleType) {
    super(name, description, BookingEntityType.GAME);
    this.consoleType = consoleType;
  }

  public String getConsoleType() {
    return consoleType;
  }

  public void setConsoleType(String consoleType) {
    this.consoleType = consoleType;
  }
}
