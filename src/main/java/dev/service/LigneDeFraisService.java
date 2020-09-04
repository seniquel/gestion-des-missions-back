package dev.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.domain.LigneDeFrais;
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

}
