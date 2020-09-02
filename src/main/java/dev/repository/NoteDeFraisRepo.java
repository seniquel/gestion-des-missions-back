package dev.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.domain.NoteDeFrais;

@Repository
public interface NoteDeFraisRepo extends JpaRepository<NoteDeFrais, UUID> {

}
