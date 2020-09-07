package dev.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import dev.domain.Mission;
import dev.domain.Statut;
import dev.domain.Transport;
import dev.repository.CollegueRepo;
import dev.repository.MissionRepo;
import dev.repository.NatureRepo;
import dev.repository.NoteDeFraisRepo;

@Service
@Transactional
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
//		NoteDeFrais noteDeFrais = new NoteDeFrais();
//		noteDeFrais.setDateDeSaisie(LocalDate.now());
		Mission mission = new Mission(dateDebut, dateFin, villeDepart, villeArrivee, prime, 
				natureRepo.findById(uuidNature).get(), collegueRepo.findById(uuidCollegue).get(), statut, transport);
		
		noteRepo.save(mission.getNoteDeFrais());
		return missionRepo.save(mission);
	}
	
	public boolean supprimer(UUID id) {
		missionRepo.deleteById(id);
		//return missionRepo.findAll().removeIf(m -> m.getUuid().equals(id));
		return !missionRepo.existsById(id);
	}
	
	@Transactional
	public Mission modifier(Mission mission, LocalDate dateDebut, LocalDate dateFin, String villeDepart, String villeArrivee,
			UUID uuidNature, Transport transport) {
		mission.setDateDebut(dateDebut);
		mission.setDateFin(dateFin);
		mission.setVilleDepart(villeDepart);
		mission.setVilleArrivee(villeArrivee);
		mission.setNature(natureRepo.findById(uuidNature).get());
		mission.setStatut(Statut.INITIALE);
		mission.setTransport(transport);
		return missionRepo.save(mission);	
	}
	
}
