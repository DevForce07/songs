package br.com.songs.web.rest.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.songs.exception.BadCredentialsCustomException;
import br.com.songs.exception.OperationException;
import br.com.songs.exception.UserNotFoundException;
import lombok.Builder;
import lombok.Data;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<RequestExceptionDetails> userNotFoundException(UserNotFoundException badRequestException) {

		return new ResponseEntity<>(
				RequestExceptionDetails.builder().timeStamps(LocalDateTime.now()).status(HttpStatus.NOT_FOUND.value())
						.title("Not found Exception, try again").details(badRequestException.getMessage())
						.developerMessge(badRequestException.getClass().getName()).build(),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BadCredentialsCustomException.class)
	public ResponseEntity<RequestExceptionDetails> userNotFoundException(
			BadCredentialsCustomException badRequestException) {

		return new ResponseEntity<>(RequestExceptionDetails.builder().timeStamps(LocalDateTime.now())
				.status(HttpStatus.UNAUTHORIZED.value()).title("Bad Credentials, try again")
				.details(badRequestException.getMessage()).developerMessge(badRequestException.getClass().getName())
				.build(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(OperationException.class)
	public ResponseEntity<RequestExceptionDetails> operationExceptionException(OperationException operationException) {

		return new ResponseEntity<>(
				RequestExceptionDetails.builder().timeStamps(LocalDateTime.now()).status(HttpStatus.BAD_REQUEST.value())
						.title("Bad Request - Operation Faild").details(operationException.getMessage())
						.developerMessge(operationException.getClass().getName()).build(),
				HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatusCode statusCode, WebRequest request) {
		return new ResponseEntity<>(
				RequestExceptionDetails.builder().timeStamps(LocalDateTime.now())
						.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).title("Error Internal")
						.details(ex.getMessage()).developerMessge(ex.getClass().getName()).build(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Builder
	@Data
	private static class RequestExceptionDetails {
		protected String title;
		protected int status;
		protected String details;
		protected String developerMessge;
		@JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss a")
		protected LocalDateTime timeStamps;

	}

}
