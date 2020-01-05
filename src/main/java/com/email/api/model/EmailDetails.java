package com.email.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Document(collection = "trackEmail")
@JsonInclude(Include.NON_NULL)
public class EmailDetails {

	@Id
	private String id;
	private String corporateTaxID;
	private String providerTin;
	private Boolean reconAlert;
	private Boolean pendAlert;
	private Boolean smartEditsAlert;
	private String reconFrequency;
	private String pendFrequency;
	private String smartEditsFrequency;
	private String reconEmailAddress;
	private String pendEmailAddress;
	private String smartEditsEmailAddress;
	private String primaryEmailAddress;

	public EmailDetails(String id, String corporateTaxID, String providerTin, Boolean reconAlert, Boolean pendAlert, Boolean smartEditsAlert,
			String reconFrequency, String pendFrequency, String smartEditsFrequency, String reconEmailAddress, String pendEmailAddress, String smartEditsEmailAddress) {
		super();
		this.id = id;
		this.corporateTaxID = corporateTaxID;
		this.providerTin = providerTin;
		this.reconAlert = reconAlert;
		this.pendAlert = pendAlert;
		this.smartEditsAlert = smartEditsAlert;
		this.reconFrequency = reconFrequency;
		this.pendFrequency = pendFrequency;
		this.smartEditsFrequency = smartEditsFrequency;
		this.reconEmailAddress = reconEmailAddress;
		this.pendEmailAddress = pendEmailAddress;
		this.smartEditsEmailAddress = smartEditsEmailAddress;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Boolean getReconAlert() {
		return reconAlert;
	}

	public void setReconAlert(Boolean reconAlert) {
		this.reconAlert = reconAlert;
	}

	public Boolean getPendAlert() {
		return pendAlert;
	}

	public void setPendAlert(Boolean pendAlert) {
		this.pendAlert = pendAlert;
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

	public String getReconEmailAddress() {
		return reconEmailAddress;
	}

	public void setReconEmailAddress(String reconEmailAddress) {
		this.reconEmailAddress = reconEmailAddress;
	}

	public String getPendEmailAddress() {
		return pendEmailAddress;
	}

	public void setPendEmailAddress(String pendEmailAddress) {
		this.pendEmailAddress = pendEmailAddress;
	}
	
	

	public String getPrimaryEmailAddress() {
		return primaryEmailAddress;
	}

	public void setPrimaryEmailAddress(String primaryEmailAddress) {
		this.primaryEmailAddress = primaryEmailAddress;
	}
	
	

	public Boolean getSmartEditsAlert() {
		return smartEditsAlert;
	}

	public void setSmartEditsAlert(Boolean smartEditsAlert) {
		this.smartEditsAlert = smartEditsAlert;
	}

	public String getSmartEditsFrequency() {
		return smartEditsFrequency;
	}

	public void setSmartEditsFrequency(String smartEditsFrequency) {
		this.smartEditsFrequency = smartEditsFrequency;
	}

	public String getSmartEditsEmailAddress() {
		return smartEditsEmailAddress;
	}

	public void setSmartEditsEmailAddress(String smartEditsEmailAddress) {
		this.smartEditsEmailAddress = smartEditsEmailAddress;
	}

	public EmailDetails() {

	}

}
