package de.alixcja.home.persistence.entity;

import jakarta.persistence.Entity;

@Entity
public class ConsoleAccessory extends BookingEntity {
  private String consoleType;

  public ConsoleAccessory(String name, String description) {
    super(name, description, BookingEntityType.CONSOLE_ACCESSORY);
  }

  public ConsoleAccessory(String name, String description, String consoleType) {
    super(name, description, BookingEntityType.CONSOLE_ACCESSORY);
    this.consoleType = consoleType;
  }

  public String getConsoleType() {
    return consoleType;
  }

  public void setConsoleType(String consoleType) {
    this.consoleType = consoleType;
  }
}
