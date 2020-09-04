package dev.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.controller.vm.CreerLigneFraisDto;
import dev.domain.LigneDeFrais;
import dev.domain.NoteDeFrais;
import dev.exception.CodeErreur;
import dev.exception.CollegueNotFoundException;
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

	@GetMapping
	public List<NoteDeFrais> lister() {
		return this.noteService.lister();
	}

	@GetMapping("{uuid}")
	public ResponseEntity<?> getLignesDeFraisOfNote(@PathVariable UUID uuid) {
		Optional<NoteDeFrais> note = noteService.getNoteByUuid(uuid);
		if (note.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(note.get());
		} else {
			throw new CollegueNotFoundException(new MessageErreurDto(CodeErreur.VALIDATION, "Cette note n'existe pas"));
		}
	}

	@PostMapping("{uuid}/ligneDeFrais")
	// TODO : vérifier que la date est entre le début et la fin de la mission
	public ResponseEntity<?> ajouterLigneDeFrais(@PathVariable UUID uuid, @RequestBody @Valid CreerLigneFraisDto ligne,
			BindingResult result) {
		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("données invalides pour la création d'une ligne de frais");
		}
		Optional<NoteDeFrais> note = noteService.getNoteByUuid(uuid);
		if (note.isPresent()) {
			// verifier doublon de ligne (date et nature)
			if (noteService.verifierDoublonLigne(ligne.getDate(), ligne.getNature(), note.get().getLignesDeFrais())) {
				// verifier que le montant des valide
				// i.e < 0, que le depassement est autorisé et que le montant est inférieur à la
				// prime
				if (noteService.verifierMontantValide(ligne.getMontant(), note.get())) {
					// ajout d'une ligne à la note
					return ResponseEntity.ok(noteService.creerLigneDeFrais(ligne.getDate(), ligne.getMontant(),
							ligne.getNature(), note.get()));
				}
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La note de frais n'existe pas");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("loupé");
	}
}
