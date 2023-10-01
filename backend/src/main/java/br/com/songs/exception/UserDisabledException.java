package br.com.songs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserDisabledException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserDisabledException() {
		super();
	}

	public UserDisabledException(String message) {
		super(message);
	}

	public UserDisabledException(Throwable cause) {
		super(cause);
	}

}
