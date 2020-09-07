package dev.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import dev.exception.CollegueException;
import dev.exception.CollegueNotFoundException;
import dev.exception.MessageErreurDto;
import dev.exception.MissionException;
import dev.exception.MissionNotFoundException;
import dev.repository.MissionRepo;
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
		if(mission.getDateDebut().isAfter(mission.getDateFin())) {
			throw new MissionException(new MessageErreurDto(CodeErreur.VALIDATION, "La date de début doit être avant la date de fin"));
		}
		if(mission.getTransport().equals("AVION") && mission.getDateDebut().isBefore(LocalDate.now().plusDays(7))) {
			throw new MissionException(new MessageErreurDto(CodeErreur.VALIDATION, "Une mission en avion nécessite au moins 7 jours d'anticipation"));
		}
		if(collegueService.seChevauchent(mission.getDateDebut(), mission.getDateFin(), this.findCollegueConnecte().get().getUuid())) {
			throw new MissionException(new MessageErreurDto(CodeErreur.VALIDATION, "Les dates de deux missions ne peuvent pas se chevaucher"));
		}
		if(mission.getDateDebut().getDayOfWeek().equals(DayOfWeek.SATURDAY) || mission.getDateDebut().getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
			throw new MissionException(new MessageErreurDto(CodeErreur.VALIDATION, "La mission ne peut pas commencer pendant le week-end"));
		}
		if(mission.getDateFin().getDayOfWeek().equals(DayOfWeek.SATURDAY) || mission.getDateFin().getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
			throw new MissionException(new MessageErreurDto(CodeErreur.VALIDATION, "La mission ne peut pas se terminer pendant le week-end"));
		}
		if(!mission.getCollegueId().equals(this.findCollegueConnecte().get().getUuid())) {
			throw new CollegueException(new MessageErreurDto(CodeErreur.METIER, "Vous n'avez pas le droit de créer une mission pour un autre collègue"));
		}
		
		return ResponseEntity.ok(missionService.creer(
				mission.getDateDebut(), 
				mission.getDateFin(), 
				mission.getVilleDepart(), 
				mission.getVilleArrivee(), 
				mission.getPrime(), 
				mission.getNatureId(), 
				mission.getCollegueId(), 
				Statut.valueOf(mission.getStatut()), 
				Transport.valueOf(mission.getTransport())));
	}
	
	@PutMapping("me/missions/{uuid}")
	public ResponseEntity<?> PutMission(@PathVariable UUID uuid, @RequestBody @Valid CreerMissionDto mission, BindingResult result){
		if (result.hasErrors()) {
			throw new MissionException(new MessageErreurDto(CodeErreur.VALIDATION, "Données invalides pour la création d'une mission"));
		}
        if(!missionService.getMission(uuid).get().getCollegue().getUuid().equals(this.findCollegueConnecte().get().getUuid())) {
    		throw new CollegueException(new MessageErreurDto(CodeErreur.METIER, "Vous n'avez pas le droit de supprimer une mission d'un autre collègue"));
    	}
		if(mission.getDateDebut().isAfter(mission.getDateFin())) {
			throw new MissionException(new MessageErreurDto(CodeErreur.VALIDATION, "La date de début doit être avant la date de fin"));
		}
		if(mission.getTransport().equals("AVION") && mission.getDateDebut().isBefore(LocalDate.now().plusDays(7))) {
			throw new MissionException(new MessageErreurDto(CodeErreur.VALIDATION, "Une mission en avion nécessite au moins 7 jours d'anticipation"));
		}
		if(collegueService.seChevauchentModif(mission.getDateDebut(), mission.getDateFin(), this.findCollegueConnecte().get().getUuid(), uuid)) {
			throw new MissionException(new MessageErreurDto(CodeErreur.VALIDATION, "Les dates de deux missions ne peuvent pas se chevaucher"));
		}
		if(mission.getDateDebut().getDayOfWeek().equals(DayOfWeek.SATURDAY) || mission.getDateDebut().getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
			throw new MissionException(new MessageErreurDto(CodeErreur.VALIDATION, "La mission ne peut pas commencer pendant le week-end"));
		}
		if(mission.getDateFin().getDayOfWeek().equals(DayOfWeek.SATURDAY) || mission.getDateFin().getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
			throw new MissionException(new MessageErreurDto(CodeErreur.VALIDATION, "La mission ne peut pas se terminer pendant le week-end"));
		}
		if(!mission.getCollegueId().equals(this.findCollegueConnecte().get().getUuid())) {
			throw new CollegueException(new MessageErreurDto(CodeErreur.VALIDATION, "Vous n'avez pas le droit de créer une mission pour un autre collègue"));
		}
		Optional<Mission> missionAModifier = missionService.getMission(uuid);
		if(!missionAModifier.isPresent()) {
			throw new MissionNotFoundException(new MessageErreurDto(CodeErreur.VALIDATION, "Cette mission n'existe pas"));
		}
		
		return ResponseEntity.ok(missionService.modifier(missionAModifier.get(), mission.getDateDebut(), mission.getDateFin(), 
				mission.getVilleDepart(), mission.getVilleArrivee(), mission.getNatureId(), Transport.valueOf(mission.getTransport())));
	}
	
	@DeleteMapping("me/missions/{uuid}")
    public ResponseEntity<UUID> deletePost(@PathVariable UUID uuid) {
        if(!missionService.getMission(uuid).get().getCollegue().getUuid().equals(this.findCollegueConnecte().get().getUuid())) {
    		throw new CollegueException(new MessageErreurDto(CodeErreur.METIER, "Vous n'avez pas le droit de supprimer une mission d'un autre collègue"));
    	}
        
        boolean isRemoved = missionService.supprimer(uuid);
        if (!isRemoved) {
            throw new MissionNotFoundException(new MessageErreurDto(CodeErreur.VALIDATION, "Cette mission n'existe pas"));
        }
        return new ResponseEntity<>(uuid, HttpStatus.OK);
	}
	
}
