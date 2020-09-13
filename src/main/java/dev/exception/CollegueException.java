package dev.exception;

public class CollegueException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private MessageErreurDto messageErreur;

	public CollegueException(MessageErreurDto messageErreur) {
		super(messageErreur.getMessage());
		this.messageErreur = messageErreur;
	}

	public MessageErreurDto getMessageErreur() {
		return messageErreur;
	}
	
}
