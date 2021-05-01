package com.email.api.model;

import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;

public class ProviderDetails {
	@Indexed(name = "providertin", direction = IndexDirection.ASCENDING)
	private String providerTin;
	private String providerName;
	private String operatorEmailAddress;

	public ProviderDetails() {
 
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

	public String getOperatorEmailAddress() {
		return operatorEmailAddress;
	}

	public void setOperatorEmailAddress(String operatorEmailAddress) {
		this.operatorEmailAddress = operatorEmailAddress;
	}

}
