package com.email.api.service;

public interface EmailService {

	void sendDailyEmail() throws Exception;
	void sendWeeklyEmail()throws Exception;
}
