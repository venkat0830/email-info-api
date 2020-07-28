package com.email.api.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "EmailAudit")
public class AuditEmailDetails {
	
	private int reconCount;
	private int weeklyReconCount;

	private int pendCount;
	private int weeklyPendCount;

	private String reconFrequency;
	private String pendFrequency;

	private String corporateTaxID;
	private String providerTin;
	private String uuID;

	private String emailAddress;
	private String createdDate;

	public int getReconCount() {
		return reconCount;
	}

	public void setReconCount(int reconCount) {
		this.reconCount = reconCount;
	}

	public int getWeeklyReconCount() {
		return weeklyReconCount;
	}

	public void setWeeklyReconCount(int weeklyReconCount) {
		this.weeklyReconCount = weeklyReconCount;
	}

	public int getPendCount() {
		return pendCount;
	}

	public void setPendCount(int pendCount) {
		this.pendCount = pendCount;
	}

	public int getWeeklyPendCount() {
		return weeklyPendCount;
	}

	public void setWeeklyPendCount(int weeklyPendCount) {
		this.weeklyPendCount = weeklyPendCount;
	}

	public String getReconFrequency() {
		return reconFrequency;
	}

	public void setReconFrequency(String reconFrequency) {
		this.reconFrequency = reconFrequency;
	}

	public String getPendFrequency() {
		return pendFrequency;
	}

	public void setPendFrequency(String pendFrequency) {
		this.pendFrequency = pendFrequency;
	}

	public String getCorporateTaxID() {
		return corporateTaxID;
	}

	public void setCorporateTaxID(String corporateTaxID) {
		this.corporateTaxID = corporateTaxID;
	}

	public String getProviderTin() {
		return providerTin;
	}

	public void setProviderTin(String providerTin) {
		this.providerTin = providerTin;
	}

	

	public String getUuID() {
		return uuID;
	}

	public void setUuID(String uuID) {
		this.uuID = uuID;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	

}
