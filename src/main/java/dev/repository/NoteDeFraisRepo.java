package dev.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.domain.NoteDeFrais;

public interface NoteDeFraisRepo extends JpaRepository<NoteDeFrais, UUID> {

}
