package dev.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.controller.vm.LigneFraisDto;
import dev.domain.LigneDeFrais;
import dev.domain.NoteDeFrais;
import dev.exception.CodeErreur;
import dev.exception.LigneDeFraisException;
import dev.exception.MessageErreurDto;
import dev.service.LigneDeFraisService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("lignesDeFrais")
public class LigneDeFraisController {
	private LigneDeFraisService ligneService;

	/**
	 * Constructeur
	 * 
	 * @param ligneService
	 */
	public LigneDeFraisController(LigneDeFraisService ligneService) {
		super();
		this.ligneService = ligneService;
	}

	@GetMapping
	public List<LigneDeFrais> getLignes() {
		return ligneService.lister();
	}

	/**
	 * méthode qui permet de supprimer une ligne de frais et de mettre a jour la
	 * note de frais associée
	 * 
	 * @param uuid : l'uuid de la ligne à supprimer
	 */
	@DeleteMapping("{uuid}")
	public ResponseEntity<Void> SupprimerLigneDeFrais(@PathVariable UUID uuid) {
		LigneDeFrais ligne = ligneService.getLigneByUuid(uuid).orElseThrow(() -> new LigneDeFraisException(
				new MessageErreurDto(CodeErreur.VALIDATION, "Cette ligne n'existe pas.")));
		ligneService.supprimerLigneDeFrais(ligne);
		return ResponseEntity.ok().build();
	}

	@PutMapping("{uuid}")
	public ResponseEntity<?> modifierLigneDeFrais(@PathVariable UUID uuid,
			@RequestBody @Valid LigneFraisDto ligneAModifier, BindingResult result) {
		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			throw new LigneDeFraisException(new MessageErreurDto(CodeErreur.VALIDATION,
					"Données invalide pour la modification d'une ligne de frais"));
		}
		LigneDeFrais ligne = ligneService.getLigneByUuid(uuid).orElseThrow(() -> new LigneDeFraisException(
				new MessageErreurDto(CodeErreur.VALIDATION, "Cette ligne n'existe pas.")));
		NoteDeFrais note = ligne.getNoteDeFrais();
		if (ligneService.verifierNouvellesInfos(ligne.getUuid(), ligne.getMontant(), ligneAModifier.getDate(),
				ligneAModifier.getNature(), ligneAModifier.getMontant(), note)) {
			// Si toutes les vérifications sont ok, ajout d'une ligne à la note
			return ResponseEntity.ok(ligneService.modifierLigneFrais(ligneAModifier.getDate(),
					ligneAModifier.getMontant(), ligneAModifier.getNature(), ligne));
		} else {
			return ResponseEntity.badRequest().body("Données non valides pour la modification d'une ligne de frais");
		}
	}
}
