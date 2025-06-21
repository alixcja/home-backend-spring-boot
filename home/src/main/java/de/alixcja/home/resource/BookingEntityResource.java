package de.alixcja.home.resource;

import de.alixcja.home.persistence.entity.BookingEntity;
import de.alixcja.home.persistence.repository.BookingEntityRepository;
import de.alixcja.home.resource.exception.BookingEntityNotFoundException;
import de.alixcja.home.resource.requestbody.ArchiveUpdateRequest;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
public class BookingEntityResource {

  @Autowired
  BookingEntityRepository bookingEntityRepository;

  @GetMapping("/entities/{id}")
  public BookingEntity getById(@PathVariable("id") Long id) {
    return bookingEntityRepository.findById(id)
            .orElseThrow(() -> new BookingEntityNotFoundException(id));
  }

  @GetMapping("/entities")
  public List<BookingEntity> getAll(@RequestParam(required = false) Boolean archived) {
    if (Objects.nonNull(archived)) {
      if (Boolean.TRUE.equals(archived)) {
        return bookingEntityRepository.findByIsArchivedTrue();
      }
      if (Boolean.FALSE.equals(archived)) {
        return bookingEntityRepository.findByIsArchivedFalse();
      }
    }
    return bookingEntityRepository.findAll();
  }

  @PatchMapping("/entities/{id}")
  @Transactional
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateArchivedStatus(@PathVariable("id") Long id, @RequestBody ArchiveUpdateRequest request) {
    BookingEntity entity = bookingEntityRepository.findById(id)
            .orElseThrow(() -> new BookingEntityNotFoundException(id));

    entity.setArchived(request.archived());
  }
}
