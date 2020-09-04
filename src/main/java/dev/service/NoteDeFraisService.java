package dev.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import dev.domain.LigneDeFrais;
import dev.domain.NoteDeFrais;
import dev.repository.LigneDeFraisRepo;
import dev.repository.MissionRepo;
import dev.repository.NoteDeFraisRepo;

@Service
public class NoteDeFraisService {
	private NoteDeFraisRepo noteRepo;
	private LigneDeFraisRepo ligneRepo;

	/**
	 * Constructeur
	 * 
	 * @param noteRepo
	 */
	public NoteDeFraisService(NoteDeFraisRepo noteRepo, LigneDeFraisRepo ligneRepo) {
		super();
		this.noteRepo = noteRepo;
		this.ligneRepo = ligneRepo;
	}

	public List<NoteDeFrais> lister() {
		return this.noteRepo.findAll();
	}

	public Optional<NoteDeFrais> getNoteByUuid(UUID uuid) {
		return this.noteRepo.findById(uuid);
	}

	/**
	 * méthode qui vérifie si un couple date/nature est déja présent dans la note de
	 * frais
	 * 
	 * @param date        : la date a vérifier
	 * @param nature      : la nature a verifier
	 * @param listeLignes : la liste de lignes a tester
	 * @return : true si non présent, false sinon
	 */
	public Boolean verifierDoublonLigne(LocalDate date, String nature, List<LigneDeFrais> listeLignes) {
		for (LigneDeFrais ligneDeFrais : listeLignes) {
			if (ligneDeFrais.getDate().compareTo(date) == 0) {
				if (ligneDeFrais.getNature().equals(nature)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * vérifie si le montant de la ligne est possible pour l'ajout d'une ligne de
	 * frais a une note
	 * 
	 * @param montant : le montant a vérifier
	 * @param note    : la note a tester, sur laquelle la ligne va s'ajouter
	 * @return : true si valide, false sinon
	 */
	public boolean verifierMontantValide(BigDecimal montant, NoteDeFrais note) {
		BigDecimal montantTotal = note.getFraisTotal().add(montant);
		BigDecimal plafondFrais = note.getMission().getNature().getPlafondFrais();
		Boolean depassement = note.getMission().getNature().getDepassementFrais();
		BigDecimal prime = note.getMission().getPrime();
		// si montant est supérieur au plafond
		if (montantTotal.compareTo(plafondFrais) == 1) {
			// si le dépassement n'est pas autorisé
			if (!depassement) {
				return false;
				// si il est autorisé mais que le total dépasse le plafond + la prime
			} else if (montantTotal.compareTo(plafondFrais.add(prime)) == 1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * méthode qui retourne true si la date de la ligne est comprise entre la date
	 * de début et la date de fin de la mission.
	 * 
	 * @param dateDebut : date de début de la mission
	 * @param dateFin   : date de fin de la mission
	 * @param dateLigne : date de la ligne de frais a ajouter
	 * @return : true si ok, false sinon
	 */
	public boolean verifierDateValide(LocalDate dateDebut, LocalDate dateFin, LocalDate dateLigne) {
		return (dateLigne.compareTo(dateDebut) >= 0 && dateLigne.compareTo(dateFin) <= 0);
	}

	/**
	 * méthode qui ajoute une ligne de frais a une note
	 * 
	 * @param date    : la date de la ligne de frais
	 * @param montant : le montant de la ligne de frais
	 * @param nature  : la nature de la ligne de frais
	 * @param note    : la note sur laquelle la ligne va se rajouter
	 * @return : la ligne de frais.
	 */
	@Transactional
	public LigneDeFrais creerLigneDeFrais(LocalDate date, BigDecimal montant, String nature, NoteDeFrais note) {
		LigneDeFrais l = new LigneDeFrais();
		l.setDate(date);
		l.setNature(nature);
		l.setMontant(montant);
		note.addLigneFrais(l);
		// entite est managed, pas de .save()
		return l;
	}

}
