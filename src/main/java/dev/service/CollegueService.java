package dev.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.domain.Collegue;
import dev.repository.CollegueRepo;

@Service
public class CollegueService {
	
	private CollegueRepo repo;
	
	public CollegueService(CollegueRepo repo) {
		this.repo = repo;
	}
	
	public List<Collegue> lister(){
		return repo.findAll();
	}
	
	public Optional<Collegue> getCollegue(UUID id){
		return repo.findById(id);
	}
	
	public Optional<Collegue> findByEmail(String email){
		return repo.findByEmail(email);
	}
}
