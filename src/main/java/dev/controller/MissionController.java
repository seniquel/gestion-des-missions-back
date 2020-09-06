package dev.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.domain.Collegue;
import dev.domain.Mission;
import dev.excel.ExportMissions;
import dev.domain.NoteDeFrais;
import dev.domain.Statut;
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
	public List<Mission> getMission() {
		return service.lister();
	}

	@GetMapping("{uuid}")
	public ResponseEntity<Optional<Mission>> getMissionByUUID(@PathVariable UUID uuid) {
		Optional<Mission> mission = service.getMission(uuid);
		if (mission.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(mission);
		} else {
			throw new CollegueNotFoundException(
					new MessageErreurDto(CodeErreur.VALIDATION, "Cette mission n'existe pas"));
		}

	}
	
	@PostMapping("missions-par-annee")
	public void creerFichierExcel(@RequestBody int annee){
		List<Mission> liste = service.getMissionCollegueConnecteParAnnee(annee);
		ExportMissions.creerFichierExcel(liste, annee);
	}
	
	@GetMapping("{annee}/prime")
	public List<Mission> getMissionParAnne(@PathVariable String annee){
		return service.getMissionCollegueConnecteParAnnee(Integer.parseInt(annee));
	}

	@GetMapping("{uuid}/noteDeFrais")
	public ResponseEntity<?> getNoteOfMission(@PathVariable UUID uuid) {
		Optional<Mission> mission = service.getMission(uuid);
		if (mission.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(mission.get().getNoteDeFrais());
		} else {
			throw new CollegueNotFoundException(
					new MessageErreurDto(CodeErreur.VALIDATION, "Cette mission n'existe pas"));
		}
	}
	
	@GetMapping("validation")
	public List<Mission> getMissionEnAttente(){
		List<Mission> missions = service.lister();
		List<Mission> enAttente = new ArrayList<>();
		for(Mission miss : missions) {
			if(miss.getStatut().equals(Statut.EN_ATTENTE_VALIDATION)) {
				enAttente.add(miss);
			}
		}
		return enAttente;
	}
	
	@PatchMapping("validation")
	public void updateStatut(@RequestParam String uuid, @RequestParam String str) {
		Mission mission = service.getMission(UUID.fromString(uuid)).get();
		
		if(str.equals("valid")) {
			mission.setStatut(Statut.VALIDEE);
		}
		else if(str.equals("rejet")) {
			mission.setStatut(Statut.REJETEE);
		}
		service.updateMission(mission);
	}
	
	@GetMapping("current")
	public List<Mission> getCurrentMission() {
		List<Mission> missions = service.listerColConnecte();
		List<Mission> missionCurrent = new ArrayList<>();
		for(Mission miss : missions) {
			if(miss.getDateDebut().isBefore(LocalDate.now()) && miss.getDateFin().isAfter(LocalDate.now())) {
				missionCurrent.add(miss);
			}
		}
		return missionCurrent;
	}
	
	@GetMapping("futur")
	public List<Mission> getFuturMission() {
		List<Mission> missions = service.listerColConnecte();
		List<Mission> missionFutur = new ArrayList<>();
		for(Mission miss : missions) {
			if(miss.getDateDebut().isAfter(LocalDate.now())) {
				missionFutur.add(miss);
			}
		}
		return missionFutur;
	}
}
