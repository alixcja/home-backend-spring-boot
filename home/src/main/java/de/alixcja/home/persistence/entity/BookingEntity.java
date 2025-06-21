package de.alixcja.home.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class BookingEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private int id;

  private String name;

  private String description;

  @Column(insertable=false, updatable=false)
  private BookingEntityType type;

  private Boolean isArchived = false;

  @CreationTimestamp
  @Column(updatable = false, nullable = false)
  private LocalDate addedOn;

  public BookingEntity() {
  }

  public BookingEntity(String name, String description, BookingEntityType type) {
    this.name = name;
    this.description = description;
    this.type = type;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BookingEntityType getType() {
    return type;
  }

  public void setType(BookingEntityType type) {
    this.type = type;
  }

  public Boolean getArchived() {
    return isArchived;
  }

  public void setArchived(Boolean archived) {
    isArchived = archived;
  }

  public LocalDate getAddedOn() {
    return addedOn;
  }
}
