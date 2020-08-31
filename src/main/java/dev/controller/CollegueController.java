package dev.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.domain.Collegue;
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
	
	@GetMapping
	public List<Collegue> getCollegues(){
		return service.lister();
	}
	
	@GetMapping("{uuid}")
	public ResponseEntity<Optional<Collegue>> getColleguetByUUID(@PathVariable UUID id) {
		Optional<Collegue> collegue = service.getCollegue(id);
		if(collegue.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(collegue);
		}
		else {
			throw new CollegueNotFoundException(new MessageErreurDto(CodeErreur.VALIDATION, "Ce collegue n'existe pas"));
		}

	}
	
}
