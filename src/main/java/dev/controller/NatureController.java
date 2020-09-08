package dev.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.controller.vm.CreerNatureDto;
import dev.domain.Nature;
import dev.service.NatureService;

@RestController
@RequestMapping("natures")
@CrossOrigin(origins = "*")
public class NatureController {
	
	private NatureService service;
	
	public NatureController(NatureService service) {
		this.service = service;
	}
	
	@GetMapping
	@Secured(value="ROLE_UTILISATEUR, ROLE_ADMINISTRATEUR, ROLE_MANAGER")
	public List<Nature> getNature(){
		return service.lister();
	}
	
	@PostMapping
	@Secured(value="ROLE_ADMINISTRATEUR")
	public ResponseEntity<?> creerNature(@RequestBody @Valid CreerNatureDto natureDto) {
		try {
			List<Nature> natures = service.lister();
			for(Nature nat : natures) {
				if(nat.getLibelle().toUpperCase().equals(natureDto.getLibelle().toUpperCase())) {
					return ResponseEntity.status(HttpStatus.CONFLICT).body("Nature existante");
				}
			}
			
			Nature natureCree = service.creer(natureDto.getLibelle(), natureDto.getPayee(), natureDto.getTjm(), 
				natureDto.getVersementPrime(), natureDto.getPourcentagePrime(), natureDto.getDebutValidite(), 
				natureDto.getPlafondFrais(), natureDto.getDepassementFrais());
			
			return ResponseEntity.status(HttpStatus.OK).body(natureDto);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}

}
