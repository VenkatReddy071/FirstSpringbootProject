package com.example.demo.utils;



public class ApiResponse<T> {

	private String message;
	private T data;
	private String responseCode;

	public ApiResponse(String message,T data,String responseCode) {
		
		this.message=message;
		this.data=data;
		this.responseCode=responseCode;
	}
	
	public static <T> ApiResponse<T> success(String message,String responseCode){
		return new ApiResponse<T>(message, null, responseCode);
	}
	
	public static <T> ApiResponse<T> failure(String message,String responseCode){
		return new ApiResponse<T>(message, null, responseCode);
	}
	
	public static <T> ApiResponse<T> successWithData(String message,T data, String responseCode){
		return new ApiResponse<T>(message, data, responseCode);
	}
	public static <T> ApiResponse<T> failureWithData(String message,T data, String responseCode){
		return new ApiResponse<T>(message, data, responseCode);
	}
	
	 public String getMessage() {
	        return message;
	    }

	 public T getData() {
	        return data;
	    }
	 public String getResponseCode() {
		 return responseCode;
	 }
	
}
