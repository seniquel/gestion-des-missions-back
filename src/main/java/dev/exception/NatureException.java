package dev.exception;

public class NatureException extends RuntimeException {
	
	private MessageErreurDto messageErreur;

	public NatureException(MessageErreurDto messageErreur) {
		super(messageErreur.getMessage());
		this.messageErreur = messageErreur;
	}

	public MessageErreurDto getMessageErreur() {
		return messageErreur;
	}
}
