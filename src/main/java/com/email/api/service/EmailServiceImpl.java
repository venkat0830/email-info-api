package com.email.api.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.email.api.model.BoTable;
import com.email.api.model.EmailDetails;
import com.email.api.model.RecordDetails;
import com.email.api.repository.EmailRepository;
import com.email.api.utilities.Constants;
import com.email.api.utilities.Error;
import com.email.api.utilities.LocalDate;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	EmailRepository emailRepository;

	@Autowired
	JavaMailSender javaMailSender;

	@Autowired
	SmartEditsService smartEditsService;

	private List<Error> validationList;

	// @Scheduled(cron = " 0 0 8 ? * MON-FRI")
	@Override
	public void sendDailyEmail(BoTable boTable) throws Exception {
		List<EmailDetails> details = emailRepository.getEmailDetails(Constants.FREQ_DAILY);
		for (EmailDetails emailDetails : details) {
			if (!isSameEmailAddress(emailDetails, Constants.FREQ_DAILY, boTable)) {
				if (!Constants.FALSE.equals(boTable.getRecon()) && emailDetails.getReconAlert() != null
						&& emailDetails.getReconAlert()
						&& Constants.FREQ_DAILY.equals(emailDetails.getReconFrequency())) {
					List<RecordDetails> recordDetails = emailRepository.getRecordList(emailDetails.getProviderTin(),
							Constants.RECORD_TYPE_RECON, Constants.FREQ_DAILY);

					if (!recordDetails.isEmpty()) {
						sendRecord(recordDetails.size(), 0, Constants.MAIL_DAILY_RECON_SUBJECT,
								emailDetails.getReconEmailAddress(), Constants.RECORD_TYPE_RECON, Constants.FREQ_DAILY,
								emailDetails.getPrimaryEmailAddress(), emailDetails.getProviderName(), false);
						System.out.println("SentEmail For Recon:" + emailDetails.getProviderTin());
					}

				}
				if (!Constants.FALSE.equals(boTable.getPend()) && emailDetails.getPendAlert() != null
						&& emailDetails.getPendAlert()
						&& Constants.FREQ_DAILY.equals(emailDetails.getPendFrequency())) {
					List<RecordDetails> recordDetails = emailRepository.getRecordList(emailDetails.getProviderTin(),
							Constants.RECORD_TYPE_PEND, Constants.FREQ_DAILY);

					if (!recordDetails.isEmpty()) {
						sendRecord(0, recordDetails.size(), Constants.MAIL_DAILY_PEND_SUBJECT,
								emailDetails.getPendEmailAddress(), Constants.RECORD_TYPE_PEND, Constants.FREQ_DAILY,
								emailDetails.getPrimaryEmailAddress(), emailDetails.getProviderName(), false);
						System.out.println("SentEmail For Pend:" + emailDetails.getProviderTin());
					}

				}
			}
		}

		if (LocalDate.isMondayToday()) {
			sendWeeklyEmail();
		}
	}

	@Override
	public void sendWeeklyEmail() throws Exception {
		List<EmailDetails> details = emailRepository.getEmailDetails(Constants.FREQ_WEEKLY);
		for (EmailDetails emailDetails : details) {
			if (!isSameEmailAddress(emailDetails, Constants.FREQ_WEEKLY)) {
				if (emailDetails.getReconAlert() != null && emailDetails.getReconAlert()

						&& Constants.FREQ_WEEKLY.equals(emailDetails.getReconFrequency())) {
					List<RecordDetails> recordDetails = emailRepository.getRecordList(emailDetails.getProviderTin(),
							Constants.RECORD_TYPE_RECON, Constants.FREQ_WEEKLY);

					if (!recordDetails.isEmpty()) {
						sendRecord(recordDetails.size(), 0, Constants.MAIL_WEEKLY_RECON_SUBJECT,
								emailDetails.getReconEmailAddress(), Constants.RECORD_TYPE_RECON, Constants.FREQ_WEEKLY,
								emailDetails.getPrimaryEmailAddress(), emailDetails.getProviderName(), false);

					}
				}
				if (emailDetails.getPendAlert() != null && emailDetails.getPendAlert()
						&& Constants.FREQ_WEEKLY.equals(emailDetails.getPendFrequency())) {
					List<RecordDetails> recordDetails = emailRepository.getRecordList(emailDetails.getProviderTin(),
							Constants.RECORD_TYPE_PEND, Constants.FREQ_WEEKLY);

					if (!recordDetails.isEmpty()) {
						sendRecord(0, recordDetails.size(), Constants.MAIL_WEEKLY_PEND_SUBJECT,
								emailDetails.getPendEmailAddress(), Constants.RECORD_TYPE_PEND, Constants.FREQ_WEEKLY,
								emailDetails.getPrimaryEmailAddress(), emailDetails.getProviderName(), false);

					}
				}
			}
		}
	}

	private void sendRecord(int reconCount, int pendCount, String subject, String emailAddress, String recordType,
			String frequency, String primaryEmailAddress, String name, boolean isSameEmailAddress) throws Exception {
		String content = getHTMLMessage(reconCount, pendCount, recordType, frequency, name, isSameEmailAddress);
		try {
			sendMail(emailAddress, subject, content);
		} catch (Exception ex) {
			throw ex;
		}
	}

	private String getHTMLMessage(int reconCount, int pendCount, String recordType, String frequency, String name,
			boolean isSameEmailAddress) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<!DOCTYPE html><html>");
		stringBuilder.append("<head></head><body> <p>Hello  ");
		if (null != name) {
			stringBuilder.append(name);
		} else {
			stringBuilder.append("Care Provider");
		}

		stringBuilder.append(",</p> <p> You have ");
		if (!isSameEmailAddress) {
			if (Constants.RECORD_TYPE_RECON.equals(recordType) && Constants.FREQ_DAILY.equals(frequency)
					&& reconCount > 0) {
				stringBuilder.append(reconCount);
				stringBuilder.append(" Reconsideration tickets that have been update in last 24hrs,");
			} else if (Constants.RECORD_TYPE_PEND.equals(recordType) && Constants.FREQ_DAILY.equals(frequency)
					&& pendCount > 0) {
				stringBuilder.append(pendCount);
				stringBuilder.append(" Pended tickets that have been update in last 24hrs,");
			} else if (Constants.RECORD_TYPE_RECON.equals(recordType) && Constants.FREQ_WEEKLY.equals(frequency)
					&& reconCount > 0) {
				stringBuilder.append(reconCount);
				stringBuilder.append(" Reconsideration tickets that have been update in last week,");
			} else if (Constants.RECORD_TYPE_PEND.equals(recordType) && Constants.FREQ_WEEKLY.equals(frequency)
					&& pendCount > 0) {
				stringBuilder.append(pendCount);
				stringBuilder.append(" Pended tickets that have been update in last week,");
			}
		} else {
			if (Constants.FREQ_DAILY.equals(frequency)) {
				if (reconCount > 0) {
					stringBuilder.append(reconCount);
					stringBuilder.append(" Reconsideration tickets that have been update in last 24hrs,");
					stringBuilder.append("</br><br> You have ");
				}
				if (pendCount > 0) {
					stringBuilder.append(pendCount);
					stringBuilder.append(" Pended tickets that have been update in last 24hrs,");
				}
			} else if (Constants.FREQ_WEEKLY.equals(frequency)) {
				if (reconCount > 0) {
					stringBuilder.append(reconCount);
					stringBuilder.append(" Reconsideration tickets that have been update in last week,");
					stringBuilder.append("</br> You have ");
				}
				if (pendCount > 0) {
					stringBuilder.append(pendCount);
					stringBuilder.append(" Pended tickets that have been update in last week,");
				}
			}
		}
		stringBuilder.append(
				" To view those update please <a href=\"https://www.google.com\" target=\"_blank\">click here</a>");
		stringBuilder.append(
				"For help using Trackit, refer to the quick reference guide or sign up for training webinar</p>");
		stringBuilder.append("</body></html>");
		return stringBuilder.toString();
	}

	private void sendMail(String to, String Subject, String text)
			throws MessagingException, UnsupportedEncodingException {
		MimeMessage mail = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail, true, "UTF-8");
		helper.setFrom("gpvenkat980@gmail.com", "Trackit");
		helper.setTo(to);
		helper.setSubject(Subject);
		helper.setText(text, true);
		javaMailSender.send(mail);
		System.out.println("sent Email: " + to);
	}

	private boolean isSameEmailAddress(EmailDetails emailDetails, String frequency, BoTable boTable) throws Exception {
		if (Constants.FALSE.equals(boTable.getPend()) && Constants.FALSE.equals(boTable.getRecon())) {
			return false;
		}
		if ((null != emailDetails.getReconAlert() && emailDetails.getReconAlert())
				&& (null != emailDetails.getPendAlert() && emailDetails.getPendAlert())) {
			if (emailDetails.getReconEmailAddress().equals(emailDetails.getPendEmailAddress())) {

				List<RecordDetails> reconDetails = emailRepository.getRecordList(emailDetails.getProviderTin(),
						Constants.RECORD_TYPE_RECON, frequency);
				List<RecordDetails> pendDetails = emailRepository.getRecordList(emailDetails.getProviderTin(),
						Constants.RECORD_TYPE_PEND, frequency);

				if (!reconDetails.isEmpty() || !pendDetails.isEmpty()) {
					sendRecord(reconDetails.size(), pendDetails.size(), Constants.MAIL_DAILY_SUBJECT,
							emailDetails.getReconEmailAddress(), Constants.RECORD_TYPE_RECON, frequency,
							emailDetails.getPrimaryEmailAddress(), emailDetails.getProviderName(), true);
					return true;
				}
			}
			return false;
		}
		return false;

	}

}
