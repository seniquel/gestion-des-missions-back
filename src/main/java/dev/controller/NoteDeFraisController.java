package dev.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.controller.vm.CreerLigneFraisDto;
import dev.domain.NoteDeFrais;
import dev.exception.CodeErreur;
import dev.exception.MessageErreurDto;
import dev.exception.NoteDeFraisException;
import dev.service.NoteDeFraisService;

@RestController
@RequestMapping("noteDeFrais")
public class NoteDeFraisController {
	private NoteDeFraisService noteService;

	/**
	 * Constructeur
	 * 
	 * @param noteService
	 */
	public NoteDeFraisController(NoteDeFraisService noteService) {
		super();
		this.noteService = noteService;
	}

	/**
	 * transforme une exception non catchée en ResponseEntity
	 * 
	 * @param ex : l'excpetion
	 * @return : une ResponseEntity
	 */
	@ExceptionHandler(NoteDeFraisException.class)
	public ResponseEntity<MessageErreurDto> onNoteDeFraisException(NoteDeFraisException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessageErreur());
	}

	/**
	 * liste les notes de frais
	 * 
	 * @return : une liste de notes de frais
	 */
	@GetMapping
	public List<NoteDeFrais> lister() {
		return this.noteService.lister();
	}

	/**
	 * @param uuid
	 * @return
	 */
	@GetMapping("{uuid}")
	public ResponseEntity<?> getLignesDeFraisOfNote(@PathVariable UUID uuid) {
		NoteDeFrais note = noteService.getNoteByUuid(uuid).orElseThrow(() -> new NoteDeFraisException(
				new MessageErreurDto(CodeErreur.VALIDATION, "Cette note n'existe pas.")));
		return ResponseEntity.status(HttpStatus.OK).body(note);
	}

	@PostMapping("{uuid}/ligneDeFrais")
	public ResponseEntity<?> ajouterLigneDeFrais(@PathVariable UUID uuid, @RequestBody @Valid CreerLigneFraisDto ligne,
			BindingResult result) {
		if (result.hasErrors()) {
			throw new NoteDeFraisException(
					new MessageErreurDto(CodeErreur.VALIDATION, "Données invalide pour l'ajout d'une ligne de frais"));
		}
		NoteDeFrais note = noteService.getNoteByUuid(uuid).orElseThrow(() -> new NoteDeFraisException(
				new MessageErreurDto(CodeErreur.VALIDATION, "Cette note n'existe pas.")));
		// verifier doublon de ligne (date et nature)
		if (!noteService.verifierDoublonLigne(ligne.getDate(), ligne.getNature(), note.getLignesDeFrais())) {
			throw new NoteDeFraisException(new MessageErreurDto(CodeErreur.VALIDATION,
					"Une ligne de frais existe déjà avec ce couple date/nature."));
		}
		// verifier que le montant des valide
		// i.e < 0, que le depassement est autorisé et que le montant est inférieur à la
		// prime
		if (!noteService.verifierMontantValide(ligne.getMontant(), note)) {
			throw new NoteDeFraisException(new MessageErreurDto(CodeErreur.VALIDATION, "Montant invalide"));
		}
		// verifier que la date est comprise entre la date de débit de la mission et
		// celle de fin
		if (!noteService.verifierDateValide(note.getMission().getDateDebut(), note.getMission().getDateFin(),
				ligne.getDate())) {
			throw new NoteDeFraisException(new MessageErreurDto(CodeErreur.VALIDATION,
					"La date doit être comprise entre la date de début et la date de fin de la mission·"));
		}
		// Si toutes les vérifications sont ok, ajout d'une ligne à la note
		return ResponseEntity
				.ok(noteService.creerLigneDeFrais(ligne.getDate(), ligne.getMontant(), ligne.getNature(), note));

	}
	
	
}
