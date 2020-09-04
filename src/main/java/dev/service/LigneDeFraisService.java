package dev.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.domain.LigneDeFrais;
import dev.domain.NoteDeFrais;
import dev.repository.LigneDeFraisRepo;

@Service
public class LigneDeFraisService {
	private LigneDeFraisRepo ligneRepo;

	/**
	 * Constructeur
	 * 
	 * @param ligneRepo
	 */
	public LigneDeFraisService(LigneDeFraisRepo ligneRepo) {
		super();
		this.ligneRepo = ligneRepo;
	}

	public List<LigneDeFrais> lister() {
		return ligneRepo.findAll();
	}

	public Optional<LigneDeFrais> getLigneByUuid(UUID uuid) {
		return ligneRepo.findById(uuid);
	}

	public void supprimerLigneDeFrais(LigneDeFrais ligne) {
		NoteDeFrais note = ligne.getNoteDeFrais();
		note.removeLigneFrais(ligne);
		ligneRepo.deleteById(ligne.getUuid());
	}

}
