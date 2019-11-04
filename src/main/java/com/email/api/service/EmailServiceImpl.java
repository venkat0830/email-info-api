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
		List<EmailDetails> details = emailRepository.getEmailDetails("Daily");
		for (EmailDetails emailDetails : details) {
			List<RecordDetails> recordDetails = emailRepository.getDocuments(emailDetails.getProviderTin());
			for (RecordDetails recDetail : recordDetails) {
				mailMessage = new SimpleMailMessage();
				mailMessage.setTo(emailDetails.getReconEmailAddress());
				System.out.println("received email:" +recDetail.getRecordInfo().getRecordLastUpdateDate());
				mailMessage.setSubject("Test Email ");

				mailMessage.setText(recDetail.getRecordInfo().getRecordId());
				javaMailSender.send(mailMessage);
			}
		}

	}

}
