  
package com.email.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.email.api.model.BoTable;
import com.email.api.model.EmailDetails;
import com.email.api.model.MissedProvider;
import com.email.api.model.MissedProviderEmailList;
import com.email.api.service.EmailService;
import com.email.api.utilities.BaseResponse;
import com.email.api.utilities.ParameterValidation;

@RestController
public class EmailNotificationController {

	@Autowired
	EmailService emailService;

	@Autowired
	ParameterValidation parameterValidation;

	@RequestMapping(value = "/retrieveDailyEmail", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<BaseResponse> getDailyEmailDetails() {
		try {
//			 ResponseEntity<Object> boTableResp = getBOTable();
//			 BoTable boTable = new BoTable();
//			 if (null != boTableResp && boTableResp.getStatusCode() == HttpStatus.OK
//			 && boTableResp.getBody() instanceof Response) {
//			 boTable = ((Response) boTableResp.getBody()).getBoTable();
//			 }
			EmailDetails emailDetails =  new EmailDetails();
			emailService.sendDailyEmail(emailDetails);
			BaseResponse response = new BaseResponse();
			response.setMessage("200 Success");
			return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception Occured:");
			BaseResponse response = new BaseResponse();
			response.setMessage("Internal server error");
			return new ResponseEntity<BaseResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

//	@RequestMapping(value = "/trackItBO", method = RequestMethod.GET, produces = "application/json")
//	public ResponseEntity<Object> getBOTable() {
//		try {
//			RestTemplate restTemplate = new RestTemplate();
//			BoTable botable = restTemplate.getForObject(url, BoTable.class);
//			Response boRes = new Response();
//			if (null != botable) {
//				boRes.setStatus("Success");
//				boRes.setBoTable(botable);
//			}
//			return new ResponseEntity<Object>(boRes, HttpStatus.OK);
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("Exception Occured:");
//			BaseResponse response = new BaseResponse();
//			response.setMessage("Internal server error");
//			return new ResponseEntity<BaseResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//
//	}

	@RequestMapping(value = "/api/provider/email", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<BaseResponse> sendMissedProviderEmails(
			@RequestBody MissedProviderEmailList missedProviderEmailList) {
		BoTable boTable = new BoTable();
		boTable.setRecon("FALSE");
		try {
			if (!CollectionUtils.isEmpty(missedProviderEmailList.getMissedProviderEmailList())) {
				List<String> fault = parameterValidation.validationRequestParam(missedProviderEmailList);
				if (!fault.isEmpty()) {
					BaseResponse resp =  new BaseResponse();
					resp.setMessage("Bad req");
					return new ResponseEntity<BaseResponse>(resp, HttpStatus.BAD_REQUEST);
				}
			}
			for (MissedProvider missedProvider : missedProviderEmailList.getMissedProviderEmailList()) {
				EmailDetails emailDetails = emailService.getProviderDetails(missedProvider.getCorporateTaxID(),
						missedProvider.getProviderTin(), missedProvider.getUuID());
				if (missedProvider.getDailyUpdateMissed() != null && missedProvider.getDailyUpdateMissed() && boTable.getRecon().equalsIgnoreCase("TRUE") ) {
					emailService.sendDailyEmail(emailDetails);
				}
				if(missedProvider.getWeeklyUpdateMissed() !=null && missedProvider.getWeeklyUpdateMissed() && boTable.getRecon().equalsIgnoreCase("TRUE")) {
					emailService.sendDailyEmail(emailDetails);
				}
			}
			BaseResponse resp = new BaseResponse();
			resp.setMessage("Success");
			return new ResponseEntity<BaseResponse>(resp, HttpStatus.OK);
		} catch (Exception ex) {
			System.out.println("Exception encountered:" + ex.getMessage());
			BaseResponse resp = new BaseResponse();
			resp.setMessage("InternalServer");
			return new ResponseEntity<BaseResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}