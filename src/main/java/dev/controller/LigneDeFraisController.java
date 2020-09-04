package dev.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.domain.LigneDeFrais;
import dev.exception.CodeErreur;
import dev.exception.LigneDeFraisException;
import dev.exception.MessageErreurDto;
import dev.service.LigneDeFraisService;

@RestController
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

	/**
	 * transforme une exception non catchée en ResponseEntity
	 * 
	 * @param ex : l'excpetion
	 * @return : une ResponseEntity
	 */
	@ExceptionHandler(LigneDeFraisException.class)
	public ResponseEntity<MessageErreurDto> onLigneDeFraisException(LigneDeFraisException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessageErreur());
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
	public void SupprimerLigneDeFrais(@PathVariable UUID uuid) {
		LigneDeFrais ligne = ligneService.getLigneByUuid(uuid).orElseThrow(() -> new LigneDeFraisException(
				new MessageErreurDto(CodeErreur.VALIDATION, "Cette ligne n'existe pas.")));
		ligneService.supprimerLigneDeFrais(ligne);
	}

}
