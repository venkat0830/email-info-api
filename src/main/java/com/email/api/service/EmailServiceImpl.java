package com.email.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.email.api.model.EmailDetails;
import com.email.api.model.RecordDetails;
import com.email.api.repository.EmailRepository;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	EmailRepository emailRepository;

	@Autowired
	JavaMailSender javaMailSender;

	@Override
	public void sendEmail() {
		SimpleMailMessage mailMessage = null;
		List<EmailDetails> details = emailRepository.getEmailDetails("reconFrequency");
		for (EmailDetails emailDetails : details) {
			mailMessage = new SimpleMailMessage();
			mailMessage.setTo(emailDetails.getReconEmailAddress());
			mailMessage.setSubject("Test Email ");
			RecordDetails recordDetails = emailRepository.getDocuments(emailDetails.getProviderTin());
			mailMessage.setText(recordDetails.getRecordInfo().getRecordId());
			javaMailSender.send(mailMessage);
		}

		
	}

}
