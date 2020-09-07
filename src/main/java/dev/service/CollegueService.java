package dev.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.domain.Collegue;
import dev.domain.Mission;
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
	

	/**
	 * Vérifie si les dates de la mission ne se chevauche pas
	 * avec les dates des autres missions du même collègue
	 * @param dateDebut
	 * @param dateFin
	 * @param id
	 * @return
	 */
	public Boolean seChevauchent(LocalDate dateDebut, LocalDate dateFin, UUID id) {
		List<Mission> listeMissions = repo.findById(id).get().getMissions();
		for (Mission mission: listeMissions) {
			if(mission.getDateFin().isAfter(dateDebut) && dateFin.isAfter(mission.getDateDebut())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Vérifie si les dates de la mission ne se chevauche pas
	 * avec les dates des autres missions du même collègue
	 * en excluant la mission à modifier
	 * @param dateDebut
	 * @param dateFin
	 * @param id
	 * @return
	 */
	public Boolean seChevauchentModif(LocalDate dateDebut, LocalDate dateFin, UUID idCollegue, UUID idMission) {
		List<Mission> listeMissions = repo.findById(idCollegue).get().getMissions();
		for (Mission mission: listeMissions) {
			if(mission.getDateFin().isAfter(dateDebut) && dateFin.isAfter(mission.getDateDebut()) && mission.getUuid()!=idMission) {
				return true;
			}
		}
		return false;
	}
}
