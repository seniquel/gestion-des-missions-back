package dev.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class NatureController {
	
	private NatureService service;
	
	public NatureController(NatureService service) {
		this.service = service;
	}
	
	

}
