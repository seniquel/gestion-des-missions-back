package dev.exception;

public class LigneDeFraisException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private MessageErreurDto messageErreur;

	public LigneDeFraisException(MessageErreurDto messageErreur) {
		super(messageErreur.getMessage());
		this.messageErreur = messageErreur;
	}

	public MessageErreurDto getMessageErreur() {
		return messageErreur;
	}
	
}
