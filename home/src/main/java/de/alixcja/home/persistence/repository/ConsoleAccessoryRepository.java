package de.alixcja.home.persistence.repository;

import de.alixcja.home.persistence.entity.ConsoleAccessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsoleAccessoryRepository extends JpaRepository<ConsoleAccessory, Long> {
}
