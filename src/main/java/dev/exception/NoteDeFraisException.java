package dev.exception;

public class NoteDeFraisException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 5281053201414304697L;
	private MessageErreurDto messageErreur;

	public NoteDeFraisException(MessageErreurDto messageErreur) {
		super(messageErreur.getMessage());
		this.messageErreur = messageErreur;
	}

	public MessageErreurDto getMessageErreur() {
		return messageErreur;
	}
	
}
