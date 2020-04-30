package com.email.api.model;

public class MissedProvider {
	
	private String corporateTaxID;
	private String providerTin;
	private String uuID;
	private Boolean weeklyUpdateMissed;
	private Boolean dailyUpdateMissed;
	private Boolean hpcUpdateMissed;

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
	public Boolean getWeeklyUpdateMissed() {
		return weeklyUpdateMissed;
	}
	public void setWeeklyUpdateMissed(Boolean weeklyUpdateMissed) {
		this.weeklyUpdateMissed = weeklyUpdateMissed;
	}
	public Boolean getDailyUpdateMissed() {
		return dailyUpdateMissed;
	}
	public void setDailyUpdateMissed(Boolean dailyUpdateMissed) {
		this.dailyUpdateMissed = dailyUpdateMissed;
	}
	public Boolean getHpcUpdateMissed() {
		return hpcUpdateMissed;
	}
	public void setHpcUpdateMissed(Boolean hpcUpdateMissed) {
		this.hpcUpdateMissed = hpcUpdateMissed;
	}
	
	

}
