package dev.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.domain.Collegue;

public interface CollegueRepo extends JpaRepository<Collegue, UUID> {

    Optional<Collegue> findByEmail(String email);
}
