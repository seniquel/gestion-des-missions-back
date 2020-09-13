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
		message.setSubject("GDM - Traitement de nuit");
		message.setText("Les missions au statut initiale ont été passé en attente de validation");
		
		javaMailSender.send(message);
	}
}
