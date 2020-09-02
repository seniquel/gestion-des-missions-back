package dev.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.domain.Collegue;
@Repository
public interface CollegueRepo extends JpaRepository<Collegue, UUID> {

    Optional<Collegue> findByEmail(String email);
}
