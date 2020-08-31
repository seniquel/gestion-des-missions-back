package dev.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepo extends JpaRepository<Mission, UUID> {

}
