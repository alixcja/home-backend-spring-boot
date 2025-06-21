package de.alixcja.home.resource.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookingEntityNotFoundException extends RuntimeException {
  public BookingEntityNotFoundException(Long id) {
    super("BookingEntity with id " + id + " not found");
  }
}