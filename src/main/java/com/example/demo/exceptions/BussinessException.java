package com.example.demo.exceptions;

public class BussinessException extends RuntimeException{

	private String message;
	private String statusCode;
	
	public BussinessException(String message,String statusCode) {
		this.message=message;
		this.statusCode=statusCode;
	}
	public String getMessage() {
		return this.message;
	}
	public String getStatusCode() {
		return this.statusCode;
	}
}
