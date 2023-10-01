package br.com.songs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserInvalidCredentialsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserInvalidCredentialsException() {
		super();
	}

	public UserInvalidCredentialsException(String message) {
		super(message);
	}

	public UserInvalidCredentialsException(Throwable cause) {
		super(cause);
	}

}
