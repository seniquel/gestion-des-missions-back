package dev.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.domain.Nature;
import dev.service.NatureService;

@RestController
@RequestMapping("natures")
public class NatureController {
	
	private NatureService service;
	
	public NatureController(NatureService service) {
		this.service = service;
	}
	
	@GetMapping
	public List<Nature> getNatures(){
		return service.lister();
	}

}
