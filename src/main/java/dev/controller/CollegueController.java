package dev.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.domain.Collegue;
import dev.domain.Mission;
import dev.exception.CodeErreur;
import dev.exception.CollegueNotFoundException;
import dev.exception.MessageErreurDto;
import dev.service.CollegueService;

@RestController
@RequestMapping("collegues")
public class CollegueController {

	private CollegueService service;
	
	public CollegueController(CollegueService service) {
		this.service = service;
	}
	
	public Optional<Collegue> findCollegueConnecte() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return service.findByEmail(email);
	}
	
	@GetMapping
	public List<Collegue> getCollegues(){
		return service.lister();
	}
	
	@GetMapping("{uuid}")
	public ResponseEntity<Optional<Collegue>> getCollegueByUUID(@PathVariable UUID uuid) {
		Optional<Collegue> collegue = service.getCollegue(uuid);
		if(collegue.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(collegue);
		}
		else {
			throw new CollegueNotFoundException(new MessageErreurDto(CodeErreur.VALIDATION, "Ce collegue n'existe pas"));
		}

	}
	
	@GetMapping("{uuid}/missions")
	public ResponseEntity<List<Mission>> getMissionsCollegueByUUID(@PathVariable UUID uuid) {
		Optional<Collegue> collegue = service.getCollegue(uuid);
		if(collegue.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(collegue.get().getMissions());
		}
		else {
			throw new CollegueNotFoundException(new MessageErreurDto(CodeErreur.VALIDATION, "Ce collegue n'existe pas"));
		}

	}
	
	@GetMapping("me")
	public ResponseEntity<Optional<Collegue>> getCollegueConnecte(){
		return ResponseEntity.status(HttpStatus.OK)
				.body(this.findCollegueConnecte());
	}
	
	@GetMapping("me/missions")
	public ResponseEntity<List<Mission>> getMissionsCollegueConnecte(){
		return ResponseEntity.status(HttpStatus.OK)
				.body(this.findCollegueConnecte().get().getMissions());
	}

}
