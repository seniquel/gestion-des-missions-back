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
import dev.domain.Mission;
import dev.exception.CodeErreur;
import dev.exception.CollegueNotFoundException;
import dev.exception.MessageErreurDto;
import dev.service.MissionService;

@RestController
@RequestMapping("missions")
public class MissionController {
	
	private MissionService service;
	
	public MissionController(MissionService service) {
		this.service = service;
	}
	
	@GetMapping
	public List<Mission> getMission(){
		return service.lister();
	}
	
	@GetMapping("{uuid}")
	public ResponseEntity<Optional<Mission>> getMissionByUUID(@PathVariable UUID id) {
		Optional<Mission> mission = service.getMission(id);
		if(mission.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(mission);
		}
		else {
			throw new CollegueNotFoundException(new MessageErreurDto(CodeErreur.VALIDATION, "Ce collegue n'existe pas"));
		}

	}

}
