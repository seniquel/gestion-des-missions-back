package dev.exception;

public class MissionNotFoundException extends MissionException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public MissionNotFoundException(MessageErreurDto messageErreur) {
		super(messageErreur);
	}
}
