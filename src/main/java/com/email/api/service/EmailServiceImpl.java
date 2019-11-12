package com.email.api.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.email.api.model.EmailDetails;
import com.email.api.model.RecordDetails;
import com.email.api.repository.EmailRepository;
import com.email.api.utilities.Constants;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	EmailRepository emailRepository;

	@Autowired
	JavaMailSender javaMailSender;

	// @Scheduled(cron = " 0 0 8 ? * MON-FRI")
	@Override
	public void sendDailyEmail() {
		List<EmailDetails> details = emailRepository.getEmailDetails(Constants.FREQ_DAILY);
		for (EmailDetails emailDetails : details) {
			if (emailDetails.getReconAlert() != null && emailDetails.getReconAlert()
					&& Constants.FREQ_DAILY.equals(emailDetails.getReconFrequency())) {
				List<RecordDetails> recordDetails = emailRepository.getRecordList(emailDetails.getProviderTin(),
						Constants.RECORD_TYPE_RECON, Constants.FREQ_DAILY);
				if (!recordDetails.isEmpty()) {
					sendRecord(recordDetails.size(), Constants.MAIL_DAILY_RECON_SUBJECT,
							emailDetails.getReconEmailAddress(), Constants.RECORD_TYPE_RECON, Constants.FREQ_DAILY);
				}

			}
			if (emailDetails.getPendAlert() != null && emailDetails.getPendAlert()
					&& Constants.FREQ_DAILY.equals(emailDetails.getPendFrequency())) {
				List<RecordDetails> recordDetails = emailRepository.getRecordList(emailDetails.getProviderTin(),
						Constants.RECORD_TYPE_PEND, Constants.FREQ_DAILY);
				if (!recordDetails.isEmpty()) {
					sendRecord(recordDetails.size(), Constants.MAIL_DAILY_PEND_SUBJECT,
							emailDetails.getPendEmailAddress(), Constants.RECORD_TYPE_PEND, Constants.FREQ_DAILY);
				}

			}
		}
	}

	@Override
	public void sendWeeklyEmail() {
		List<EmailDetails> details = emailRepository.getEmailDetails(Constants.FREQ_DAILY);
		for (EmailDetails emailDetails : details) {
			if (emailDetails.getReconAlert() != null && emailDetails.getReconAlert()

					&& Constants.FREQ_WEEKLY.equals(emailDetails.getReconFrequency())) {
				List<RecordDetails> recordDetails = emailRepository.getRecordList(emailDetails.getProviderTin(),
						Constants.RECORD_TYPE_RECON, Constants.FREQ_WEEKLY);
				sendRecord(recordDetails.size(), Constants.MAIL_WEEKLY_RECON_SUBJECT,
						emailDetails.getReconEmailAddress(), Constants.RECORD_TYPE_RECON, Constants.FREQ_WEEKLY);

			}
			if (emailDetails.getPendAlert() != null && emailDetails.getPendAlert()
					&& Constants.FREQ_WEEKLY.equals(emailDetails.getPendFrequency())) {
				List<RecordDetails> recordDetails = emailRepository.getRecordList(emailDetails.getProviderTin(),
						Constants.RECORD_TYPE_PEND, Constants.FREQ_WEEKLY);
				for (RecordDetails recDetails : recordDetails) {
					sendRecord(recordDetails.size(), Constants.MAIL_DAILY_PEND_SUBJECT,
							emailDetails.getPendEmailAddress(), Constants.RECORD_TYPE_PEND, Constants.FREQ_WEEKLY);

				}
			}
		}
	}

	private void sendRecord(int count, String subject, String emailAddress, String recordType, String frequency) {
		String content = getHTMLMessage(count, recordType, frequency);
		sendMail(emailAddress, subject, content);
	}

	private String getHTMLMessage(int count, String recordType, String frequency) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<!DOCTYPE html><html>");
		stringBuilder.append("<head></head><body> <p>Hello Care Provider,</p> <p> You have ");
		stringBuilder.append(count);
		if (Constants.RECORD_TYPE_RECON.equals(recordType) && Constants.FREQ_DAILY.equals(frequency)) {
			stringBuilder.append(" Reconsideration tickets that have been update in last 24hrs,");
		}
		else if (Constants.RECORD_TYPE_PEND.equals(recordType) && Constants.FREQ_DAILY.equals(frequency)) {
			stringBuilder.append(" Pended tickets that have been update in last 24hrs,");
		}
		else if (Constants.RECORD_TYPE_RECON.equals(recordType) && Constants.FREQ_WEEKLY.equals(frequency)) {
			stringBuilder.append(" Reconsideration tickets that have been update in last week,");
		}
		else if (Constants.RECORD_TYPE_PEND.equals(recordType) && Constants.FREQ_WEEKLY.equals(frequency)) {
			stringBuilder.append(" Pended tickets that have been update in last week,");
		}
		stringBuilder.append(
				" To view those update please <a href=\"https://www.google.com\" target=\"_blank\">click here</a>");
		stringBuilder.append(
				"For help using Trackit, refer to the quick reference guide or sign up for training webinar</p>");
		stringBuilder.append("</body></html>");
		return stringBuilder.toString();
	}

	private void sendMail(String to, String Subject, String text) {
		MimeMessage mail = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mail, true);
			helper.setTo(to);
			helper.setSubject(Subject);
			helper.setText(text, true);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
		javaMailSender.send(mail);

	}

}
