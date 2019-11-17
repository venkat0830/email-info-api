package com.email.api.utilities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {
	
	private List<Error> error;
	private String message;
	public List<Error> getError() {
		return error;
	}
	public void setError(List<Error> error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

}
