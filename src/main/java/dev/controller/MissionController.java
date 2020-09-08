package dev.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import dev.domain.Statut;
import dev.excel.ExportMissions;
import dev.exception.CodeErreur;
import dev.exception.CollegueNotFoundException;
import dev.exception.MessageErreurDto;
import dev.exception.PrimeException;
import dev.service.EmailService;
import dev.service.CollegueService;
import dev.service.MissionService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("missions")
public class MissionController {

	private MissionService service;
	private CollegueService collegueService;
  private EmailService serviceMail;
	
	Logger logger = Logger.getLogger(MissionController.class.getName());

	public MissionController(MissionService service, CollegueService collegueService, EmailService serviceMail) {
		this.service = service;
		this.collegueService = collegueService;
    this.serviceMail = serviceMail;
	}
	
	public Optional<Collegue> findCollegueConnecte() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return collegueService.findByEmail(email);
	}
	
	public List<Mission> listerColConnecte() {
		return this.findCollegueConnecte().get().getMissions();
	}
	
	public List<Mission> getMissionCollegueConnecteParAnnee(int annee) {
		List<Mission> missions = this.findCollegueConnecte().get().getMissions();
		List<Mission> missionsParAnnee = new ArrayList<>();
		if (annee == LocalDate.now().getYear()) {
			for (Mission miss : missions) {
				if (miss.getDateFin().getYear() == annee && miss.getDateFin().compareTo(LocalDate.now()) < 0) {
					missionsParAnnee.add(miss);
				}
			}
		} else {
			for (Mission miss : missions) {
				if (miss.getDateFin().getYear() == annee) {
					missionsParAnnee.add(miss);
				}
			}
		}
		return missionsParAnnee;
	}

	@GetMapping
	@Secured(value="ROLE_UTILISATEUR, ROLE_ADMINISTRATEUR, ROLE_MANAGER")
	public List<Mission> getMission() {
		return service.lister();
	}

	@GetMapping("{uuid}")
	@Secured(value="ROLE_UTILISATEUR, ROLE_ADMINISTRATEUR, ROLE_MANAGER")
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
	@Secured(value="ROLE_UTILISATEUR, ROLE_ADMINISTRATEUR, ROLE_MANAGER")
	public void creerFichierExcel(@RequestBody int annee) {
		List<Mission> liste = this.getMissionCollegueConnecteParAnnee(annee);
		ExportMissions.creerFichierExcel(liste, annee);
	}

	@GetMapping("{annee}/prime")
	@Secured(value="ROLE_UTILISATEUR, ROLE_ADMINISTRATEUR, ROLE_MANAGER")
	public List<Mission> getMissionParAnne(@PathVariable String annee) {
		return this.getMissionCollegueConnecteParAnnee(Integer.parseInt(annee));
	}

	@GetMapping("{uuid}/noteDeFrais")
	@Secured(value="ROLE_UTILISATEUR, ROLE_ADMINISTRATEUR, ROLE_MANAGER")
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
	@Secured(value="ROLE_MANAGER")
	public List<Mission> getMissionEnAttente() {
		List<Mission> missions = service.lister();
		List<Mission> enAttente = new ArrayList<>();
		for (Mission miss : missions) {
			if (miss.getStatut().equals(Statut.EN_ATTENTE_VALIDATION)) {
				enAttente.add(miss);
			}
		}
		return enAttente;
	}

	@PatchMapping("validation")
	@Secured(value="ROLE_MANAGER")
	public void updateStatut(@RequestParam String uuid, @RequestParam String str) {
		Mission mission = service.getMission(UUID.fromString(uuid)).get();

		if (str.equals("valid")) {
			mission.setStatut(Statut.VALIDEE);
		} else if (str.equals("rejet")) {
			mission.setStatut(Statut.REJETEE);
		}
		service.updateMission(mission);
	}

	@GetMapping("current")
	@Secured(value="ROLE_UTILISATEUR, ROLE_ADMINISTRATEUR, ROLE_MANAGER")
	public List<Mission> getCurrentMission() {
		List<Mission> missions = this.listerColConnecte();
		List<Mission> missionCurrent = new ArrayList<>();
		for (Mission miss : missions) {
			if ((miss.getDateDebut().isBefore(LocalDate.now()) || miss.getDateDebut().isEqual(LocalDate.now()))
					&& miss.getDateFin().isAfter(LocalDate.now())) {
				missionCurrent.add(miss);
			}
		}
		return missionCurrent;
	}

	@GetMapping("futur")
	@Secured(value="ROLE_UTILISATEUR, ROLE_ADMINISTRATEUR, ROLE_MANAGER")
	public List<Mission> getFuturMission() {
		List<Mission> missions = this.listerColConnecte();
		List<Mission> missionFutur = new ArrayList<>();
		for (Mission miss : missions) {
			if (miss.getDateDebut().isAfter(LocalDate.now())) {
				missionFutur.add(miss);
			}
		}
		return missionFutur;
	}

	@GetMapping("traitement")
	@Scheduled(cron = "0 4 * * * *")
	public void updateValidation() {
		
		List<Mission> missions = service.lister();

		for (Mission miss : missions) {
					
			if (miss.getStatut().equals(Statut.INITIALE)) {
				miss.setStatut(Statut.EN_ATTENTE_VALIDATION);
				service.updateMission(miss);
				logger.info("Mission validée ==> " + miss.toString());
				serviceMail.sendEmail();
			}
			
			if (miss.getDateFin().isBefore(LocalDate.now()) && !miss.getTraite()) {
				Period joursTravailles = Period.between(miss.getDateDebut(), miss.getDateFin());

				if (miss.getNature().getTjm() == null || miss.getNature().getPourcentagePrime() == null) {
					throw new PrimeException(new MessageErreurDto(CodeErreur.METIER, "Le TJM ou le pourcentage de la prime est incorrect"));
				}
				
				BigDecimal calculPrime = BigDecimal.valueOf(joursTravailles.getDays())
						.multiply(miss.getNature().getTjm()).multiply(miss.getNature().getPourcentagePrime()
								.divide(BigDecimal.valueOf(100), 2, RoundingMode.CEILING));

				if (miss.getNature().getDepassementFrais()) {
					calculPrime.subtract(miss.getNature().getPlafondFrais());
				}
				
				miss.setPrime(calculPrime);
				miss.setTraite(true);
				service.updateMission(miss);
				logger.info("Prime de la mission terminée : " + miss.getPrime().toString() + " ==> " + miss.toString());	
			}
		}
	}
}
