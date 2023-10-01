package br.com.songs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class OperationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OperationException() {
		super();
	}

	public OperationException(String message) {
		super(message);
	}

	public OperationException(Throwable cause) {
		super(cause);
	}

}
