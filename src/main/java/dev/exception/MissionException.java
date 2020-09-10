package dev.exception;

public class MissionException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private MessageErreurDto messageErreur;

	public MissionException(MessageErreurDto messageErreur) {
		super(messageErreur.getMessage());
		this.messageErreur = messageErreur;
	}

	public MessageErreurDto getMessageErreur() {
		return messageErreur;
	}
	
}
