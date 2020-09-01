package dev.exception;

public class MissionException extends RuntimeException {

	private MessageErreurDto messageErreur;

	public MissionException(MessageErreurDto messageErreur) {
		super(messageErreur.getMessage());
		this.messageErreur = messageErreur;
	}

	public MessageErreurDto getMessageErreur() {
		return messageErreur;
	}
	
}
