package dev.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.domain.LigneDeFrais;

public interface LigneDeFraisRepo extends JpaRepository<LigneDeFrais, UUID> {

}
