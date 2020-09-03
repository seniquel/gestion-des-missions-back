package dev.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.domain.LigneDeFrais;
import dev.service.LigneDeFraisService;

@RestController
@RequestMapping("lignesDeFrais")
public class LigneDeFraisController {
	private LigneDeFraisService ligneService;

	/** Constructeur
	 * @param ligneService
	 */
	public LigneDeFraisController(LigneDeFraisService ligneService) {
		super();
		this.ligneService = ligneService;
	}
	
	@GetMapping
	public List<LigneDeFrais> getLignes() {
		return this.ligneService.lister();
	}
	

}
