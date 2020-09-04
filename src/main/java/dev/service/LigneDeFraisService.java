package dev.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import dev.domain.LigneDeFrais;
import dev.domain.NoteDeFrais;
import dev.exception.CodeErreur;
import dev.exception.LigneDeFraisException;
import dev.exception.MessageErreurDto;
import dev.repository.LigneDeFraisRepo;

@Service
public class LigneDeFraisService {
	private NoteDeFraisService noteService;
	private LigneDeFraisRepo ligneRepo;

	/**
	 * Constructeur
	 * 
	 * @param noteService
	 * @param ligneRepo
	 */
	public LigneDeFraisService(NoteDeFraisService noteService, LigneDeFraisRepo ligneRepo) {
		super();
		this.noteService = noteService;
		this.ligneRepo = ligneRepo;
	}

	public List<LigneDeFrais> lister() {
		return ligneRepo.findAll();
	}

	public Optional<LigneDeFrais> getLigneByUuid(UUID uuid) {
		return ligneRepo.findById(uuid);
	}

	/**
	 * méthode pour supprimer un eligne de frais par son id, et de mettre la note de
	 * frais a jour
	 * 
	 * @param ligne : la ligne a supprimer
	 */
	@Transactional
	public void supprimerLigneDeFrais(LigneDeFrais ligne) {
		NoteDeFrais note = ligne.getNoteDeFrais();
		note.removeLigneFrais(ligne);
		note.getSignatureNumerique().setDateMaj(LocalDate.now());
		ligneRepo.deleteById(ligne.getUuid());
	}

	/**
	 * méthode qui permet de modifier une ligne, et de mettre a jour la note de
	 * frais
	 * 
	 * @param date
	 * @param montant
	 * @param nature
	 * @param ligne
	 * @return
	 */
	@Transactional
	public LigneDeFrais modifierLigneFrais(LocalDate date, BigDecimal montant, String nature, LigneDeFrais ligne) {
		ligne.setDate(date);
		ligne.setMontant(montant);
		ligne.setNature(nature);
		// recalcul des frais totaux de la note
		ligne.getNoteDeFrais().calculerFraisTotal();
		ligne.getNoteDeFrais().getSignatureNumerique().setDateMaj(LocalDate.now());
		return ligneRepo.save(ligne);
	}

	/**
	 * vérifie les informations pour que la nouvelle ligne soit valide
	 * 
	 * @param uuid           : l'uuid de la ligne
	 * @param ancienMontant  : l'ancien montant de la ligne
	 * @param date           : la nouvelle date a ajouter
	 * @param nature         : la nouvelle nature a ajouter
	 * @param nouveauMontant : le nouveau montant a ajouter
	 * @param note           : la note rattachée
	 * @return : true si ok, false sinon
	 */
	public boolean verifierNouvellesInfos(UUID uuid, BigDecimal ancienMontant, LocalDate date, String nature,
			BigDecimal nouveauMontant, NoteDeFrais note) {
		if (!verifierDoublonLigne(uuid, date, nature, note.getLignesDeFrais())) {
			throw new LigneDeFraisException(new MessageErreurDto(CodeErreur.VALIDATION,
					"Une ligne de frais existe déjà avec ce couple date/nature."));
		}
		if (!verifierMontantAModifier(ancienMontant, nouveauMontant, note)) {
			throw new LigneDeFraisException(new MessageErreurDto(CodeErreur.VALIDATION, "montant invalide"));
		}
		if (!noteService.verifierDateValide(note.getMission().getDateDebut(), note.getMission().getDateFin(), date)) {
			throw new LigneDeFraisException(new MessageErreurDto(CodeErreur.VALIDATION,
					"La date doit être comprise entre la date de début et la date de fin de la mission·"));
		}
		return true;
	}

	/**
	 * vérifie si le nouveau montant est ok. Pour cela, il faut enlever l'ancien et
	 * rajouter le nouveau pour le test
	 * 
	 * @param ancienMontant  : le montant a enlever
	 * @param nouveauMontant : le monant a ajouter
	 * @param note           : la note rattachée
	 * @return true si ok, false sinon
	 */
	private boolean verifierMontantAModifier(BigDecimal ancienMontant, BigDecimal nouveauMontant, NoteDeFrais note) {
		BigDecimal montantTotal = note.getFraisTotal().subtract(ancienMontant).add(nouveauMontant);
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
	 * vérifie si la ligne n'est pas déjà présente, sans se vérifier elle-même
	 * 
	 * @param uuid          : l'uuid a ne pas tester
	 * @param date          : la date a tester
	 * @param nature        : la nature a tester
	 * @param lignesDeFrais : les lignes de frais a tester
	 * @return : true si ok, false sinon
	 */
	private boolean verifierDoublonLigne(UUID uuid, LocalDate date, String nature, List<LigneDeFrais> lignesDeFrais) {
		for (LigneDeFrais ligneDeFrais : lignesDeFrais) {
			if (!ligneDeFrais.getUuid().equals(uuid)) {
				if (ligneDeFrais.getDate().compareTo(date) == 0) {
					if (ligneDeFrais.getNature().equals(nature)) {
						return false;
					}
				}
			}
		}
		return true;
	}

}
