package com.email.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.email.api.model.EmailDetails;
import com.email.api.service.EmailService;
import com.email.api.utilities.BaseResponse;

@RestController
public class EmailNotificationController {

	@Autowired
	EmailService emailService;

	@RequestMapping(value = "/retrieveWeeklyEmail", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<BaseResponse> getWeeklyEmailDetails() {
		try {

			emailService.sendWeeklyEmail();
			BaseResponse response = new BaseResponse();
			response.setMessage("200 Success");
			return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Exception Occured:");
			BaseResponse response = new BaseResponse();
			response.setMessage("Internal server error");
			return new ResponseEntity<BaseResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/retrieveDailyEmail", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<BaseResponse> getDailyEmailDetails() {
		try {

			emailService.sendDailyEmail();
			BaseResponse response = new BaseResponse();
			response.setMessage("200 Success");
			return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println("Exception Occured:");
			BaseResponse response = new BaseResponse();
			response.setMessage("Internal server error");
			return new ResponseEntity<BaseResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
