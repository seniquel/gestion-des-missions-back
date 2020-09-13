package dev.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.domain.LigneDeFrais;

@Repository
public interface LigneDeFraisRepo extends JpaRepository<LigneDeFrais, UUID> {

}
