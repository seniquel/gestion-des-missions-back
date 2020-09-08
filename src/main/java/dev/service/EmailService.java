package dev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;

	public void sendEmail() {
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("be3837f76a-0e53e3@inbox.mailtrap.io");
		message.setTo("manager@dev.fr");
		message.setSubject("Gestion des Missions - Traitement de nuit");
		message.setText("Missions au statut initiale passées en attente de validation");
		
		javaMailSender.send(message);
	}
}
