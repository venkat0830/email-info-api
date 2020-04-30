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
	public final String sucessResponse = "Success";
	public final String badResponse = "Bad req";

	@When("Client calls \\/api\\/provider\\/email API with MissedProviderEmailList")
	public void client_calls_api_provider_email_API_with_MissedProviderEmailList() {
		MissedProviderEmailList request = getMissedProviderList();
		ResponseEntity<BaseResponse> result = restTemplate.postForEntity(DEFAULT_URL + "api/provider/email", request,
				BaseResponse.class);
		if (result.getStatusCode().equals(HttpStatus.OK)) {
			baseResponse = result.getBody();
		}
	}

	@Then("BaseResponse should be Success")
	public void baseresponse_should_be_Success() {
		assertEquals(sucessResponse, baseResponse.getMessage());
	}

//	@When("Client calls \\/api\\/provider\\/email API  without providing corporateTaxID in MissedProviderEmailList")
//	public void client_calls_api_provider_email_API_with_empty_MissedProviderEmailList() {
//		MissedProviderEmailList request = new MissedProviderEmailList();
//		List<MissedProvider> missedProviderEmailList = new ArrayList<MissedProvider>();
//		MissedProvider missedProvider = new MissedProvider();
//		missedProvider.setProviderTin("1233423");
//		missedProvider.setUuID("98763456");
//		missedProviderEmailList.add(missedProvider);
//		request.setMissedProviderEmailList(missedProviderEmailList);
//		ResponseEntity<BaseResponse> result = restTemplate.postForEntity(DEFAULT_URL + "api/provider/email", request,
//				BaseResponse.class);
//		if (result.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
//			baseResponse = result.getBody();
//		}
//	}
//	
//	@When("Client calls \\/api\\/provider\\/email API  without providing providerTin in MissedProviderEmailList")
//	public void client_calls_api_provider_email_API_without_providing_providerTin_in_MissedProviderEmailList() {
//		MissedProviderEmailList request = new MissedProviderEmailList();
//		List<MissedProvider> missedProviderEmailList = new ArrayList<MissedProvider>();
//		MissedProvider missedProvider = new MissedProvider();
//		missedProvider.setCorporateTaxID("123456789");
//		missedProvider.setUuID("98763456");
//		missedProviderEmailList.add(missedProvider);
//		request.setMissedProviderEmailList(missedProviderEmailList);
//		ResponseEntity<BaseResponse> result = restTemplate.postForEntity(DEFAULT_URL + "api/provider/email", request,
//				BaseResponse.class);
//		if (result.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
//			baseResponse = result.getBody();
//		}
//	}
//
//	@When("Client calls \\/api\\/provider\\/email API  without providing uuID in MissedProviderEmailList")
//	public void client_calls_api_provider_email_API_without_providing_uuID_in_MissedProviderEmailList() {
//		MissedProviderEmailList request = new MissedProviderEmailList();
//		List<MissedProvider> missedProviderEmailList = new ArrayList<MissedProvider>();
//		MissedProvider missedProvider = new MissedProvider();
//		missedProvider.setCorporateTaxID("123456789");
//		missedProvider.setProviderTin("1233423");
//		missedProviderEmailList.add(missedProvider);
//		request.setMissedProviderEmailList(missedProviderEmailList);
//		ResponseEntity<BaseResponse> result = restTemplate.postForEntity(DEFAULT_URL + "api/provider/email", request,
//				BaseResponse.class);
//		if (result.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
//			baseResponse = result.getBody();
//		}
//	}
//
//	@Then("BaseResponse should be Bad Request")
//	public void baseresponse_should_be_Bad_Request() {
//		assertEquals(badResponse, baseResponse.getMessage());
//	}
//
	private MissedProviderEmailList getMissedProviderList() {
		MissedProviderEmailList request = new MissedProviderEmailList();
		List<MissedProvider> missedProviderEmailList = new ArrayList<MissedProvider>();
		MissedProvider missedProvider = new MissedProvider();
		missedProvider.setCorporateTaxID("8623789562");
		missedProvider.setProviderTin("809667005");
		missedProvider.setUuID("12345-12345");
		missedProvider.setDailyUpdateMissed(true);
		missedProvider.setWeeklyUpdateMissed(true);
		missedProviderEmailList.add(missedProvider);
		request.setMissedProviderEmailList(missedProviderEmailList);
		return request;
	}


}
