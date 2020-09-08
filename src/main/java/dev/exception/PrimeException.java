package dev.exception;

public class PrimeException extends RuntimeException {

	private MessageErreurDto messageErreur;

	public PrimeException(MessageErreurDto messageErreur) {
		super(messageErreur.getMessage());
		this.messageErreur = messageErreur;
	}

	public MessageErreurDto getMessageErreur() {
		return messageErreur;
	}
}
