package com.email.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.email.api.controller.EmailNotificationController;
import com.email.api.model.AuditEmailDetails;
//import com.email.api.service.EmailService;
import com.email.api.service.EmailServiceImpl;
import com.email.api.utilities.BaseResponse;

@RunWith(SpringRunner.class)
public class TestEmailController {

	EmailNotificationController emailNotificationController;

	@MockBean
	EmailServiceImpl emailService;

	@Before
	public void setup() throws Exception {
		emailNotificationController = new EmailNotificationController();
	}

	@Test
	public void successTest() throws Exception {
		String emailAdd = "test123@optum.com";
		emailNotificationController.setEmailService(emailService);
		Mockito.doNothing().when(emailService).sendDailyEmail(emailAdd);
		ResponseEntity<BaseResponse> resp = emailNotificationController.getEmailDetails(emailAdd);
		assertEquals("200 Success", resp.getBody().getMessage());
	}

	@Test
	public void intErrTest() throws Exception {
		String emailAdd = "test123@optum.com";
		emailNotificationController.setEmailService(emailService);
		Mockito.doThrow(Exception.class).when(emailService).sendDailyEmail(emailAdd);
		ResponseEntity<BaseResponse> resp = emailNotificationController.getEmailDetails(emailAdd);
		assertEquals("Internal server error", resp.getBody().getMessage());
	}

	@Test
	public void auditSuccessTest() throws Exception {
		String emailAdd = "test123@optum.com";
		String providerTin = "123456789";
		String uuID = "12345";
		emailNotificationController.setEmailService(emailService);
		Mockito.when(emailService.getAuditEmailDetails(emailAdd, providerTin, uuID))
				.thenReturn(new ArrayList<AuditEmailDetails>());
		ResponseEntity<Object> resp = emailNotificationController.trackitInfo(emailAdd, providerTin, uuID);
		assertEquals(HttpStatus.OK, resp.getStatusCode());
	}

}
