package dev.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import dev.exception.CollegueException;
import dev.exception.CollegueNotFoundException;
import dev.exception.MessageErreurDto;
import dev.exception.MissionException;
import dev.exception.MissionNotFoundException;

@ControllerAdvice
public class GestionErreurCtrlAdvice {

	@ExceptionHandler(CollegueException.class)
	public ResponseEntity<MessageErreurDto> quandCollegueException(CollegueException ex) {
		return ResponseEntity.badRequest().body(ex.getMessageErreur());	
	}
	
	@ExceptionHandler(CollegueNotFoundException.class)
	public ResponseEntity<MessageErreurDto> quandClientNotFoundException(CollegueNotFoundException ex) {
		return ((BodyBuilder) ResponseEntity.notFound()).body(ex.getMessageErreur());	
	}
	
	@ExceptionHandler(MissionException.class)
	public ResponseEntity<MessageErreurDto> quandMissionException(MissionException ex) {
		return ResponseEntity.badRequest().body(ex.getMessageErreur());	
	}
	
	@ExceptionHandler(MissionNotFoundException.class)
	public ResponseEntity<MessageErreurDto> quandMissionNotFoundException(MissionNotFoundException ex) {
		return ((BodyBuilder) ResponseEntity.notFound()).body(ex.getMessageErreur());	
	}
	
}
