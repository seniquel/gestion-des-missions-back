package dev.exception;

public class PrimeException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private MessageErreurDto messageErreur;

	public PrimeException(MessageErreurDto messageErreur) {
		super(messageErreur.getMessage());
		this.messageErreur = messageErreur;
	}

	public MessageErreurDto getMessageErreur() {
		return messageErreur;
	}
}
