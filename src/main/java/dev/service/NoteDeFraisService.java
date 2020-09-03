package dev.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.domain.NoteDeFrais;
import dev.repository.MissionRepo;
import dev.repository.NoteDeFraisRepo;

@Service
public class NoteDeFraisService {
	private NoteDeFraisRepo noteRepo;

	/** Constructeur
	 * @param noteRepo
	 */
	public NoteDeFraisService(NoteDeFraisRepo noteRepo) {
		super();
		this.noteRepo = noteRepo;
	}
	
	public List<NoteDeFrais> lister() {
		return this.noteRepo.findAll();
	}


}
