package com.ola.booking.exceptions;

public class NoAvailableRidersException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NoAvailableRidersException(String msg) {
		super(msg);
	}

}
