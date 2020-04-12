package com.email.api.service;

import com.email.api.model.BoTable;
import com.email.api.model.EmailDetails;

public interface EmailService {

	void sendDailyEmail(EmailDetails emailDetails) throws Exception;
	//void sendWeeklyEmail()throws Exception;
	
	EmailDetails getProviderDetails(String corporateTaxID, String providerTin, String uuID);
}
