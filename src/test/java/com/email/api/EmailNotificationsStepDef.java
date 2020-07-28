package com.email.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.email.api.model.MissedProvider;
import com.email.api.model.MissedProviderEmailList;
import com.email.api.utilities.BaseResponse;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class EmailNotificationsStepDef extends SpringIntegrationTest {

	BaseResponse baseResponse;
	public final String sucessResponse = "200 Success";
	public final String badResponse = "Bad req";

//	@When("Client calls \\/api\\/provider\\/email API with MissedProviderEmailList")
//	public void client_calls_api_provider_email_API_with_MissedProviderEmailList() {
//		MissedProviderEmailList request = getMissedProviderList();
//		ResponseEntity<BaseResponse> result = restTemplate.postForEntity(DEFAULT_URL + "api/provider/email", request,
//				BaseResponse.class);
//		if (result.getStatusCode().equals(HttpStatus.OK)) {
//			baseResponse = result.getBody();
//		}
//	}

	@Then("BaseResponse should be Success")
	public void baseresponse_should_be_Success() {
		assertEquals(sucessResponse, baseResponse.getMessage());
	}

//	private MissedProviderEmailList getMissedProviderList() {
//		MissedProviderEmailList request = new MissedProviderEmailList();
//		List<MissedProvider> missedProviderEmailList = new ArrayList<MissedProvider>();
//		MissedProvider missedProvider = new MissedProvider();
//		missedProvider.setCorporateTaxID("8623789562");
//		missedProvider.setProviderTin("809667005");
//		missedProvider.setUuID("12345-12345");
//		missedProvider.setDailyUpdateMissed(true);
//		missedProvider.setWeeklyUpdateMissed(true);
//		missedProviderEmailList.add(missedProvider);
//		request.setMissedProviderEmailList(missedProviderEmailList);
//		return request;
//	}

	@When("client calls \\/retrieveDailyEmail to request the getEmailDetails")
	public void client_calls_retrieveDailyEmail_to_request_the_getEmailDetails() {
		ResponseEntity<BaseResponse> result = restTemplate.getForEntity(DEFAULT_URL + "retrieveDailyEmail",
				BaseResponse.class);
		if (result.getStatusCode().equals(HttpStatus.OK)) {
			baseResponse = result.getBody();
		}
	}

//	@When("when Operator email address is same as priamry emaill address or preferred email address")
//	public void when_Operator_email_address_is_same_as_priamry_emaill_address_or_preferred_email_address() {
//		assertEquals(sucessResponse, baseResponse.getMessage());
//	}

//	@Then("Send Email to the given email address")
//	public void send_Email_to_the_given_email_address() {
//		assertEquals(sucessResponse, baseResponse.getMessage());
//	}

	@When("BoTable Toggle functionalities should be turned on")
	public void botable_Toggle_functionalities_should_be_turned_on() {

		ResponseEntity<BaseResponse> result = restTemplate.getForEntity(DEFAULT_URL + "retrieveDailyEmail",
				BaseResponse.class);
		if (result.getStatusCode().equals(HttpStatus.OK)) {
			baseResponse = result.getBody();
		}
	}

	@When("BoTable Toggle functionalities turned off")
	public void botable_Toggle_functionalities_turned_off() {
		ResponseEntity<BaseResponse> result = restTemplate.getForEntity(DEFAULT_URL + "retrieveDailyEmail",
				BaseResponse.class);
		if (result.getStatusCode().equals(HttpStatus.OK)) {
			baseResponse = result.getBody();
		}

	}

	@Then("Don't need to send the emails")
	public void don_t_need_to_send_the_emails() {
		assertEquals(sucessResponse, baseResponse.getMessage());
	}

}
