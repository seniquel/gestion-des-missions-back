package dev.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.domain.NoteDeFrais;
import dev.service.NoteDeFraisService;

@RestController
@RequestMapping("noteDeFrais")
public class NoteDeFraisController {
	private NoteDeFraisService noteService;

	/** Constructeur
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

}
