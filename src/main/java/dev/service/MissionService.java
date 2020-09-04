package dev.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.controller.CollegueController;
import dev.domain.Collegue;
import dev.domain.Mission;
import dev.repository.MissionRepo;

@Service
public class MissionService {
	
	private MissionRepo repo;
	private CollegueController colCtrl;
	
	public MissionService(MissionRepo repo, CollegueController colCtrl) {
		this.repo = repo;
		this.colCtrl = colCtrl;
	}

	public List<Mission> lister(){
		return repo.findAll();
	}
	
	public Optional<Mission> getMission(UUID id){
		return repo.findById(id);
	}
	
	public List<Mission> getMissionCollegueConnecteParAnnee(int annee){
		List<Mission> missions = colCtrl.findCollegueConnecte().get().getMissions();
		List<Mission> missionsParAnnee = new ArrayList<>();
		if(annee == LocalDate.now().getYear()) {
			for(Mission miss : missions) {
				if(miss.getDateFin().getYear() == annee && miss.getDateFin().compareTo(LocalDate.now())<0) {
					missionsParAnnee.add(miss);
				}
			}
		}
		else {
			for(Mission miss : missions) {
			if(miss.getDateFin().getYear() == annee) {
				missionsParAnnee.add(miss);
				}
			}
		}
		
		return missionsParAnnee;
	}
	
}
