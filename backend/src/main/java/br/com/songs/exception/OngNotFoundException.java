package br.com.songs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class OngNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OngNotFoundException() {
		super();
	}

	public OngNotFoundException(String message) {
		super(message);
	}

	public OngNotFoundException(Throwable cause) {
		super(cause);
	}

}
