package com.email.api.model;

public class ClaimDetails {

	private String claimNumber;
	private String claimFirstDateOfService;

	public ClaimDetails() {

	}

	public ClaimDetails(String claimNumber, String claimFirstDateOfService) {
		super();
		this.claimNumber = claimNumber;
		this.claimFirstDateOfService = claimFirstDateOfService;
	}

	public String getClaimNumber() {
		return claimNumber;
	}

	public void setClaimNumber(String claimNumber) {
		this.claimNumber = claimNumber;
	}

	public String getClaimFirstDateOfService() {
		return claimFirstDateOfService;
	}

	public void setClaimFirstDateOfService(String claimFirstDateOfService) {
		this.claimFirstDateOfService = claimFirstDateOfService;
	}

}

