package com.email.api.model;

public class ProviderDetails {
	
	private String providerTin;
	private String providerName;
	
	public ProviderDetails( ) {
	
	}
	public ProviderDetails(String providerTin, String providerName) {
		super();
		this.providerTin = providerTin;
		this.providerName = providerName;
	}
	public String getProviderTin() {
		return providerTin;
	}
	public void setProviderTin(String providerTin) {
		this.providerTin = providerTin;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}	
	

}
