package dev.exception;

public class MessageErreurDto {
	
	private CodeErreur code;
	
	private String message;
	public MessageErreurDto(CodeErreur code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public CodeErreur getCode() {
		return code;
	}

	public void setCode(CodeErreur code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

}
