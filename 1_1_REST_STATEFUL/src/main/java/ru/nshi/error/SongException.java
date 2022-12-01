package ru.nshi.error;

import ru.nshi.model.Error;
import org.springframework.http.HttpStatus;

public class SongException extends RuntimeException {
	private HttpStatus status;
	public SongException(String msg, HttpStatus status) {
		super(msg);
		this.status = status;
	}

	public Error getError() {
		return new Error(this.getMessage());
	}

	public HttpStatus getStatus() {
		return status;
	}
}
