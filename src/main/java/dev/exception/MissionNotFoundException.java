package dev.exception;

public class MissionNotFoundException extends MissionException {

	public MissionNotFoundException(MessageErreurDto messageErreur) {
		super(messageErreur);
	}
}
