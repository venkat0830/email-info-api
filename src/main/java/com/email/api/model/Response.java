package com.email.api.model;

import org.springframework.util.MultiValueMap;

public class Response {
	
	BoTable boTable;
	String status;
	String errorResp;
	MultiValueMap<String, String> headerMap;
	public BoTable getBoTable() {
		return boTable;
	}
	public void setBoTable(BoTable boTable) {
		this.boTable = boTable;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorResp() {
		return errorResp;
	}
	public void setErrorResp(String errorResp) {
		this.errorResp = errorResp;
	}
	public MultiValueMap<String, String> getHeaderMap() {
		return headerMap;
	}
	public void setHeaderMap(MultiValueMap<String, String> headerMap) {
		this.headerMap = headerMap;
	}
	
	

}
