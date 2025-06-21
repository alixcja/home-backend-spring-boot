package de.alixcja.home.persistence.repository;

import de.alixcja.home.persistence.entity.Console;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsoleRepository extends JpaRepository<Console, Long> {
}
