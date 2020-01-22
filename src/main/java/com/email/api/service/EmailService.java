package com.email.api.service;

import com.email.api.model.BoTable;

public interface EmailService {

	void sendDailyEmail() throws Exception;
	void sendWeeklyEmail()throws Exception;
}
