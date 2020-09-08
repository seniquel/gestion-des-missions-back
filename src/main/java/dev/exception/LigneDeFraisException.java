package dev.exception;

public class LigneDeFraisException extends RuntimeException {

	private MessageErreurDto messageErreur;

	public LigneDeFraisException(MessageErreurDto messageErreur) {
		super(messageErreur.getMessage());
		this.messageErreur = messageErreur;
	}

	public MessageErreurDto getMessageErreur() {
		return messageErreur;
	}
	
}
