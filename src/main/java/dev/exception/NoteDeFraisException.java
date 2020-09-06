package dev.exception;

public class NoteDeFraisException extends RuntimeException {

	private MessageErreurDto messageErreur;

	public NoteDeFraisException(MessageErreurDto messageErreur) {
		super(messageErreur.getMessage());
		this.messageErreur = messageErreur;
	}

	public MessageErreurDto getMessageErreur() {
		return messageErreur;
	}
	
}
