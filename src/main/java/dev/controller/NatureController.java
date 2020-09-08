package dev.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public List<Nature> getNature(){
		return service.lister();
	}
	
	@GetMapping("valides")
	public List<Nature> getNatureValides(){
		List<Nature> liste = service.lister();
		List<Nature> valides = new ArrayList<>();
		for(Nature nat : liste) {
			if(nat.getFinValidite()==null) {
				valides.add(nat);
			}
		}
		return valides;
	}
	
	@GetMapping("{uuid}")
	public Nature getNatureUuid(@PathVariable UUID uuid) {
		return service.getNature(uuid).get();
	}
	
	@PostMapping
	public ResponseEntity<?> creerNature(@RequestBody @Valid CreerNatureDto natureDto) {
		try {
			List<Nature> natures = service.lister();
			for(Nature nat : natures) {
				if(nat.getLibelle().toUpperCase().equals(natureDto.getLibelle().toUpperCase())) {
					return ResponseEntity.status(HttpStatus.CONFLICT).body("Nature existante");
				}
			}
			
			Nature natureCree = service.creer(natureDto.getLibelle(), natureDto.getPayee(), natureDto.getTjm(), 
				natureDto.getVersementPrime(), natureDto.getPourcentagePrime(), 
				natureDto.getPlafondFrais(), natureDto.getDepassementFrais());
			
			return ResponseEntity.status(HttpStatus.OK).body(natureDto);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}
	
	@PostMapping("post")
	public ResponseEntity<?> creerNatureLibelleExistant(@RequestBody @Valid CreerNatureDto natureDto) {
		try {
			List<Nature> natures = service.lister();
			
			Nature natureCree = service.creer(natureDto.getLibelle(), natureDto.getPayee(), natureDto.getTjm(), 
				natureDto.getVersementPrime(), natureDto.getPourcentagePrime(), 
				natureDto.getPlafondFrais(), natureDto.getDepassementFrais());
			
			return ResponseEntity.status(HttpStatus.OK).body(natureDto);
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
	}
	
	@PatchMapping("updateDateFin/{uuid}")
	public void updateDateFin(@PathVariable UUID uuid) {
		//mettre la date de fin à aujourd'hui
		Nature nature = service.getNature(uuid).get();
		nature.setFinValidite(LocalDate.now());
		service.update(nature);
	}
	
	@PatchMapping("modifier/{uuid}")
	public void updateNature(@PathVariable UUID uuid, @RequestBody @Valid CreerNatureDto natureDto) {
		//changer la nature sans changer la date de début
		Nature nature = service.getNature(uuid).get();
		nature.setLibelle(natureDto.getLibelle());
		nature.setPayee(natureDto.getPayee());
		nature.setTjm(natureDto.getTjm());
		nature.setVersementPrime(natureDto.getVersementPrime());
		nature.setPourcentagePrime(natureDto.getPourcentagePrime());
		nature.setDepassementFrais(natureDto.getDepassementFrais());
		nature.setPlafondFrais(natureDto.getPlafondFrais());
		
		service.update(nature);
	}

}
