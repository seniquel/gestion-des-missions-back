package dev.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.domain.Collegue;
import dev.domain.Mission;
import dev.domain.Nature;
import dev.domain.NoteDeFrais;
import dev.domain.Statut;
import dev.domain.Transport;
import dev.repository.CollegueRepo;
import dev.repository.MissionRepo;
import dev.repository.NatureRepo;
import dev.repository.NoteDeFraisRepo;

@Service
public class MissionService {
	
	private MissionRepo missionRepo;
	private NatureRepo natureRepo;
	private CollegueRepo collegueRepo;
	private NoteDeFraisRepo noteRepo;
	
	public MissionService(MissionRepo repo, NatureRepo natureRepo, CollegueRepo collegueRepo, NoteDeFraisRepo noteRepo) {
		this.missionRepo = repo;
		this.natureRepo = natureRepo;
		this.collegueRepo = collegueRepo;
		this.noteRepo = noteRepo;
	}

	public List<Mission> lister(){
		return missionRepo.findAll();
	}
	
	public Optional<Mission> getMission(UUID id){
		return missionRepo.findById(id);
	}
	
	public Mission creer(LocalDate dateDebut, LocalDate dateFin, String villeDepart, String villeArrivee, BigDecimal prime,
			UUID uuidNature, UUID uuidCollegue, Statut statut, Transport transport) {
		
		Mission mission = new Mission(dateDebut, dateFin, villeDepart, villeArrivee, prime, 
				natureRepo.findById(uuidNature).get(), collegueRepo.findById(uuidCollegue).get(), statut, transport);
		noteRepo.save(mission.getNoteDeFrais());
		return missionRepo.save(mission);
	}
	
}
