package dev.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.domain.LigneDeFrais;
import dev.domain.NoteDeFrais;
import dev.exception.CodeErreur;
import dev.exception.CollegueNotFoundException;
import dev.exception.MessageErreurDto;
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
			throw new CollegueNotFoundException(
					new MessageErreurDto(CodeErreur.VALIDATION, "Cette note n'existe pas"));
		}
	}

}
