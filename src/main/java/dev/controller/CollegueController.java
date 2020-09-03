package dev.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.controller.vm.CreerMissionDto;
import dev.controller.vm.MissionDto;
import dev.domain.Collegue;
import dev.domain.Mission;
import dev.domain.Statut;
import dev.domain.Transport;
import dev.exception.CodeErreur;
import dev.exception.CollegueNotFoundException;
import dev.exception.MessageErreurDto;
import dev.exception.MissionException;
import dev.service.CollegueService;
import dev.service.MissionService;

@RestController
@RequestMapping("collegues")
public class CollegueController {

	private CollegueService collegueService;
	private MissionService missionService;
	
	public CollegueController(CollegueService service, MissionService missionService) {
		this.collegueService = service;
		this.missionService = missionService;
	}
	
	public Optional<Collegue> findCollegueConnecte() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return collegueService.findByEmail(email);
	}
	
	@GetMapping
	public List<Collegue> getCollegues(){
		return collegueService.lister();
	}
	
	@GetMapping("{uuid}")
	public ResponseEntity<Optional<Collegue>> getCollegueByUUID(@PathVariable UUID uuid) {
		Optional<Collegue> collegue = collegueService.getCollegue(uuid);
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
		Optional<Collegue> collegue = collegueService.getCollegue(uuid);
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
	
	@PostMapping("me/missions")
	public ResponseEntity<?> PostMission(@RequestBody @Valid CreerMissionDto mission, BindingResult result){
		if (result.hasErrors()) {
			throw new MissionException(new MessageErreurDto(CodeErreur.VALIDATION, "Données invalides pour la création d'une mission"));
		}
		Mission missionCreee = missionService.creer(
				mission.getDateDebut(), 
				mission.getDateFin(), 
				mission.getVilleDepart(), 
				mission.getVilleArrivee(), 
				mission.getPrime(), 
				mission.getNatureId(), 
				mission.getCollegueId(), 
				Statut.valueOf(mission.getStatut()), 
				Transport.valueOf(mission.getTransport()));
		
		MissionDto missionDto = new MissionDto();
		missionDto.setUuid(missionCreee.getUuid());
		missionDto.setDateDebut(missionCreee.getDateDebut());
		missionDto.setDateFin(missionCreee.getDateFin());
		missionDto.setVilleDepart(missionCreee.getVilleDepart());
		missionDto.setVilleArrivee(missionCreee.getVilleArrivee());
		missionDto.setPrime(missionCreee.getPrime());
		missionDto.setNatureId(missionCreee.getNature().getUuid());
		missionDto.setCollegueId(missionCreee.getCollegue().getUuid());
		missionDto.setStatut(missionCreee.getStatut().toString());
		missionDto.setTransport(missionCreee.getTransport().toString());
		
		return ResponseEntity.ok(missionDto);
	}
}
