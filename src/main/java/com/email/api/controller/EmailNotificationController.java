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

@RestController
public class EmailNotificationController {

	@Autowired
	EmailService emailService;

	@RequestMapping(value = "/retrieveDailyEmail", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Object> getEmailDetails() {

	     emailService.sendDailyEmail();
	    // emailService.sendDailyPendEmail(); 
		// emailService.sendWeeklyEmail();

		return new ResponseEntity<Object>(HttpStatus.OK);

	}

}
