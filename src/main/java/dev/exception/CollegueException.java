package dev.exception;

public class CollegueException extends RuntimeException {

	private MessageErreurDto messageErreur;

	public CollegueException(MessageErreurDto messageErreur) {
		super(messageErreur.getMessage());
		this.messageErreur = messageErreur;
	}

	public MessageErreurDto getMessageErreur() {
		return messageErreur;
	}
	
}
