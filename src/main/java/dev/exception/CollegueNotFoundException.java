package dev.exception;

public class CollegueNotFoundException extends CollegueException {

	public CollegueNotFoundException(MessageErreurDto messageErreur) {
		super(messageErreur);
	}
}
