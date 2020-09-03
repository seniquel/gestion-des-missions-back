package dev.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.domain.Mission;
import dev.repository.MissionRepo;

@Service
public class MissionService {
	
	private MissionRepo repo;
	
	public MissionService(MissionRepo repo) {
		this.repo = repo;
	}

	public List<Mission> lister(){
		return repo.findAll();
	}
	
	public Optional<Mission> getMission(UUID id){
		return repo.findById(id);
	}
	
	
}
