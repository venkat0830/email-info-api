package com.email.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Document(collection = "trackitEmailReg")
@JsonInclude(Include.NON_NULL)
public class EmailDetails {

	@Id
	private String id;
	private String corporateTaxID;
	private String providerTin;
	private Boolean reconAlert;
	private Boolean pendAlert;
	private Boolean smartEditsAlert;
	private Boolean hpcEditsAlert;
	private Boolean appealsAlert;
	private Boolean mrmAlert;
	private String reconFrequency;
	private String pendFrequency;
	private String smartEditsFrequency;
	private String reconEmailAddress;
	private String pendEmailAddress;
	private String smartEditsEmailAddress;
	private String appealEmailAddress;
	private String mrmEmailAddress;
	private String hpsEmailAddress;
	private String primaryEmailAddress;
	private String providerName;

	public EmailDetails(String id, String corporateTaxID, String providerTin, Boolean reconAlert, Boolean pendAlert,
			Boolean smartEditsAlert, String reconFrequency, String pendFrequency, String smartEditsFrequency,
			String reconEmailAddress, String pendEmailAddress, String smartEditsEmailAddress, String providerName) {
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
		this.providerName = providerName;
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

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public Boolean getHpcEditsAlert() {
		return hpcEditsAlert;
	}

	public void setHpcEditsAlert(Boolean hpcEditsAlert) {
		this.hpcEditsAlert = hpcEditsAlert;
	}

	public Boolean getAppealsAlert() {
		return appealsAlert;
	}

	public void setAppealsAlert(Boolean appealsAlert) {
		this.appealsAlert = appealsAlert;
	}

	public Boolean getMrmAlert() {
		return mrmAlert;
	}

	public void setMrmAlert(Boolean mrmAlert) {
		this.mrmAlert = mrmAlert;
	}

	public String getAppealEmailAddress() {
		return appealEmailAddress;
	}

	public void setAppealEmailAddress(String appealEmailAddress) {
		this.appealEmailAddress = appealEmailAddress;
	}

	public String getMrmEmailAddress() {
		return mrmEmailAddress;
	}

	public void setMrmEmailAddress(String mrmEmailAddress) {
		this.mrmEmailAddress = mrmEmailAddress;
	}

	public String getHpsEmailAddress() {
		return hpsEmailAddress;
	}

	public void setHpsEmailAddress(String hpsEmailAddress) {
		this.hpsEmailAddress = hpsEmailAddress;
	}

	public EmailDetails() {

	}

}

