package br.com.songs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class BadCredentialsCustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BadCredentialsCustomException() {
		super();
	}

	public BadCredentialsCustomException(String message) {
		super(message);
	}

	public BadCredentialsCustomException(Throwable cause) {
		super(cause);
	}
}
