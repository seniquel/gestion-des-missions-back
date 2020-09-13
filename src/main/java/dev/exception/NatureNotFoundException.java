package dev.exception;

public class NatureNotFoundException extends NatureException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public NatureNotFoundException(MessageErreurDto messageErreur) {
		super(messageErreur);
	}

}
