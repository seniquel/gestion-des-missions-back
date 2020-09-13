package dev.exception;

public class NatureException extends RuntimeException {
	
	/**
	 *
	 */
	private static final long serialVersionUID = 2179156588957961507L;
	private MessageErreurDto messageErreur;

	public NatureException(MessageErreurDto messageErreur) {
		super(messageErreur.getMessage());
		this.messageErreur = messageErreur;
	}

	public MessageErreurDto getMessageErreur() {
		return messageErreur;
	}
}
