package com.email.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Document(collection = "trackitEmailReg2")
@JsonInclude(Include.NON_NULL)
public class EmailDetails {

	@Id
	private String id;
	private String uuID;
	private String corporateProviderMpin;
	private String providerTin;
	public String getAppealsEmailAddress() {
		return appealsEmailAddress;
	}

	public void setAppealsEmailAddress(String appealsEmailAddress) {
		this.appealsEmailAddress = appealsEmailAddress;
	}

	private Boolean reconAlert;
	private Boolean pendAlert;
	private Boolean smartEditAlert;
	private Boolean hpcEditsAlert;
	private Boolean appealsAlert;
	private Boolean mrmAlert;
	private String reconFrequency;
	private String pendFrequency;
	private String smartEditFrequency;
	private String reconEmailAddress;
	private String pendEmailAddress;
	private String smartEditEmailAddress;
	private String appealsEmailAddress;
	private String mrmEmailAddress;
	private String hpsEmailAddress;
	private String appealsFrequency;
//	@Indexed(name = "primaryemailaddress", direction = IndexDirection.ASCENDING)
	private String primaryEmailAddress;
	private String providerName;
	private Boolean isSameEmailAddress;
	private Boolean pharmaCouponAlert;
	private String pharmaCouponEmailAddress;
	private String pharmaCouponFrequency;

//	public EmailDetails(String id, String corporateProviderMpin, String providerTin, Boolean reconAlert, Boolean pendAlert,
//			Boolean smartEditsAlert, String reconFrequency, String pendFrequency, String smartEditFrequency,
//			String reconEmailAddress, String pendEmailAddress, String smartEditEmailAddress, String providerName,
//			Boolean isSameEmailAddress) {
//		super();
//		this.id = id;
//		this.corporateProviderMpin = corporateProviderMpin;
//		this.providerTin = providerTin;
//		this.reconAlert = reconAlert;
//		this.pendAlert = pendAlert;
//		this.smartEditAlert = smartEditsAlert;
//		this.reconFrequency = reconFrequency;
//		this.pendFrequency = pendFrequency;
//		this.smartEditFrequency = smartEditsFrequency;
//		this.reconEmailAddress = reconEmailAddress;
//		this.pendEmailAddress = pendEmailAddress;
//		this.smartEditEmailAddress = smartEditsEmailAddress;
//		this.providerName = providerName;
//		this.isSameEmailAddress = isSameEmailAddress;
//	}

	public Boolean getSmartEditAlert() {
		return smartEditAlert;
	}

	public void setSmartEditAlert(Boolean smartEditAlert) {
		this.smartEditAlert = smartEditAlert;
	}

	public String getSmartEditFrequency() {
		return smartEditFrequency;
	}

	public void setSmartEditFrequency(String smartEditFrequency) {
		this.smartEditFrequency = smartEditFrequency;
	}

	public String getSmartEditEmailAddress() {
		return smartEditEmailAddress;
	}

	public void setSmartEditEmailAddress(String smartEditEmailAddress) {
		this.smartEditEmailAddress = smartEditEmailAddress;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public String getCorporateProviderMpin() {
		return corporateProviderMpin;
	}

	public void setCorporateProviderMpin(String corporateProviderMpin) {
		this.corporateProviderMpin = corporateProviderMpin;
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

	public String getUuID() {
		return uuID;
	}

	public void setUuID(String uuID) {
		this.uuID = uuID;
	}

	public EmailDetails() {

	}

	public Boolean getIsSameEmailAddress() {
		return isSameEmailAddress;
	}

	public void setIsSameEmailAddress(Boolean isSameEmailAddress) {
		this.isSameEmailAddress = isSameEmailAddress;
	}

	public Boolean getPharmaCouponAlert() {
		return pharmaCouponAlert;
	}

	public void setPharmaCouponAlert(Boolean pharmaCouponAlert) {
		this.pharmaCouponAlert = pharmaCouponAlert;
	}

	public String getPharmaCouponEmailAddress() {
		return pharmaCouponEmailAddress;
	}

	public void setPharmaCouponEmailAddress(String pharmaCouponEmailAddress) {
		this.pharmaCouponEmailAddress = pharmaCouponEmailAddress;
	}

	public String getPharmaCouponFrequency() {
		return pharmaCouponFrequency;
	}

	public void setPharmaCouponFrequency(String pharmaCouponFrequency) {
		this.pharmaCouponFrequency = pharmaCouponFrequency;
	}

	public String getAppealsFrequency() {
		return appealsFrequency;
	}

	public void setAppealsFrequency(String appealsFrequency) {
		this.appealsFrequency = appealsFrequency;
	}
	
	

}
