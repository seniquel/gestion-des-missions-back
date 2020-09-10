package dev.exception;

public class CollegueNotFoundException extends CollegueException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public CollegueNotFoundException(MessageErreurDto messageErreur) {
		super(messageErreur);
	}
}
