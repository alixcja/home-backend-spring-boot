package de.alixcja.home.persistence.repository;

import de.alixcja.home.persistence.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingEntityRepository extends JpaRepository<BookingEntity, Long> {
  List<BookingEntity> findByIsArchivedTrue();

  List<BookingEntity> findByIsArchivedFalse();
}
