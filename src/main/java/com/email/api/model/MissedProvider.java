package com.email.api.model;

public class MissedProvider {
	
	private String corporateTaxID;
	private String providerTin;
	private String uuID;
	private String weeklyUpdateMissed;
	private String dailyUpdateMissed;
	private String hpcUpdateMissed;

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
	public String getWeeklyUpdateMissed() {
		return weeklyUpdateMissed;
	}
	public void setWeeklyUpdateMissed(String weeklyUpdateMissed) {
		this.weeklyUpdateMissed = weeklyUpdateMissed;
	}
	public String getDailyUpdateMissed() {
		return dailyUpdateMissed;
	}
	public void setDailyUpdateMissed(String dailyUpdateMissed) {
		this.dailyUpdateMissed = dailyUpdateMissed;
	}
	public String getHpcUpdateMissed() {
		return hpcUpdateMissed;
	}
	public void setHpcUpdateMissed(String hpcUpdateMissed) {
		this.hpcUpdateMissed = hpcUpdateMissed;
	}
	
	

}
