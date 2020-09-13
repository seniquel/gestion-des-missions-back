package dev.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.controller.vm.LigneFraisDto;
import dev.domain.NoteDeFrais;
import dev.exception.CodeErreur;
import dev.exception.MessageErreurDto;
import dev.exception.NoteDeFraisException;
import dev.service.NoteDeFraisService;

@RestController
@CrossOrigin(origins = "*")
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

	/**
	 * méthode qui permet l'ajout d'une nouvelle ligne de frais
	 * 
	 * @param uuid   : l'uuid de la note sur laquelle la ligne va etre ajoutée
	 * @param ligne  : la ligne a ajouter
	 * @param result
	 * @return : la ligne nouvellement créée
	 */
	@PostMapping("{uuid}/ligneDeFrais")
	public ResponseEntity<?> ajouterLigneDeFrais(@PathVariable UUID uuid, @RequestBody @Valid LigneFraisDto ligne,
			BindingResult result) {
		if (result.hasErrors()) {
			throw new NoteDeFraisException(
					new MessageErreurDto(CodeErreur.VALIDATION, "Données invalide pour l'ajout d'une ligne de frais"));
		}
		NoteDeFrais note = noteService.getNoteByUuid(uuid).orElseThrow(() -> new NoteDeFraisException(
				new MessageErreurDto(CodeErreur.VALIDATION, "Cette note n'existe pas.")));
		if (noteService.verifierInfos(ligne.getDate(), ligne.getNature(), ligne.getMontant(), note)) {
			// Si toutes les vérifications sont ok, ajout d'une ligne à la note
			return ResponseEntity
					.ok(noteService.creerLigneDeFrais(ligne.getDate(), ligne.getMontant(), ligne.getNature(), note));
		} else {
			return ResponseEntity.badRequest().body("Données non valides pour l'ajout d'une ligne de frais");
		}
	}
}
