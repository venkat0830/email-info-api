package com.email.api.service;

import java.io.UnsupportedEncodingException;
import java.net.PasswordAuthentication;
import java.util.List;

import javax.mail.MailSessionDefinition;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.email.api.model.EmailDetails;
import com.email.api.model.Notification;
import com.email.api.model.RecordDetails;
import com.email.api.repository.EmailRepository;
import com.email.api.utilities.Constants;
import com.email.api.utilities.Error;

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
	public void sendDailyEmail() throws Exception {
		List<EmailDetails> details = emailRepository.getEmailDetails(Constants.FREQ_DAILY);
		for (EmailDetails emailDetails : details) {
			if ((emailDetails.getReconAlert() != null && emailDetails.getReconAlert())
					&& (emailDetails.getPendAlert() != null && emailDetails.getPendAlert())) {
				if (Constants.FREQ_DAILY.equals(emailDetails.getReconFrequency())
						&& Constants.FREQ_DAILY.equals(emailDetails.getPendFrequency())
						&& (emailDetails.getReconEmailAddress().equals(emailDetails.getPendEmailAddress()))) {
					List<RecordDetails> reconDetails = emailRepository.getRecordList(emailDetails.getProviderTin(),
							Constants.RECORD_TYPE_RECON, Constants.FREQ_DAILY);
					List<RecordDetails> pendDetails = emailRepository.getRecordList(emailDetails.getProviderTin(),
							Constants.RECORD_TYPE_PEND, Constants.FREQ_DAILY);
					if (!reconDetails.isEmpty() || !pendDetails.isEmpty()) {

						sendRecord(reconDetails.size(), pendDetails.size(), Constants.MAIL_DAILY_SUBJECT,
								emailDetails.getReconEmailAddress(), Constants.RECORD_TYPE_RECON, Constants.FREQ_DAILY,
								emailDetails.getPrimaryEmailAddress(), emailDetails.getProviderName(), true);
						System.out.println("SentEmail For both the record types recon adn Pend:" +emailDetails.getProviderTin());
					}
				}
			} else {
				if (emailDetails.getReconAlert() != null && emailDetails.getReconAlert()

						&& Constants.FREQ_DAILY.equals(emailDetails.getReconFrequency())) {
					List<RecordDetails> recordDetails = emailRepository.getRecordList(emailDetails.getProviderTin(),
							Constants.RECORD_TYPE_RECON, Constants.FREQ_DAILY);

					if (!recordDetails.isEmpty()) {
						sendRecord(recordDetails.size(), 0, Constants.MAIL_DAILY_RECON_SUBJECT,
								emailDetails.getReconEmailAddress(), Constants.RECORD_TYPE_RECON, Constants.FREQ_DAILY,
								emailDetails.getPrimaryEmailAddress(), emailDetails.getProviderName(), false);
						System.out.println("SentEmail For Recon:" +emailDetails.getProviderTin());
					}

				}
				if (emailDetails.getPendAlert() != null && emailDetails.getPendAlert()
						&& Constants.FREQ_DAILY.equals(emailDetails.getPendFrequency())) {
					List<RecordDetails> recordDetails = emailRepository.getRecordList(emailDetails.getProviderTin(),
							Constants.RECORD_TYPE_PEND, Constants.FREQ_DAILY);

					if (!recordDetails.isEmpty()) {
						sendRecord(0, recordDetails.size(), Constants.MAIL_DAILY_PEND_SUBJECT,
								emailDetails.getPendEmailAddress(), Constants.RECORD_TYPE_PEND, Constants.FREQ_DAILY,
								emailDetails.getPrimaryEmailAddress(), emailDetails.getProviderName(), false);
						System.out.println("SentEmail For Pend:" +emailDetails.getProviderTin());
					}

				}
			}
			// if (emailDetails.getSmartEditsAlert() != null &&
			// emailDetails.getSmartEditsAlert()
			// && Constants.FREQ_DAILY.equals(emailDetails.getSmartEditsFrequency())) {
			// List<Notification> notifications = smartEditsService
			// .getSmartEditsNotificationList(emailDetails.getProviderTin());
			// if (!notifications.isEmpty()) {
			// sendRecord(notifications.size(), 0, Constants.MAIL_DAILY_SMARTEDITS_SUBJECT,
			// emailDetails.getSmartEditsEmailAddress(), Constants.RECORD_TYPE_SMARTEDITS,
			// Constants.FREQ_DAILY, emailDetails.getPrimaryEmailAddress(),
			// emailDetails.getProviderName(), false);
			// }
			// }
		}
	}

	@Override
	public void sendWeeklyEmail() throws Exception {
		List<EmailDetails> details = emailRepository.getEmailDetails(Constants.FREQ_WEEKLY);
		for (EmailDetails emailDetails : details) {
			if ((emailDetails.getReconAlert() != null && emailDetails.getReconAlert())
					&& (emailDetails.getPendAlert() != null && emailDetails.getPendAlert())) {
				if (Constants.FREQ_WEEKLY.equals(emailDetails.getReconFrequency())
						&& Constants.FREQ_WEEKLY.equals(emailDetails.getPendFrequency())
						&& (emailDetails.getReconEmailAddress().equals(emailDetails.getPendEmailAddress()))) {
					List<RecordDetails> reconDetails = emailRepository.getRecordList(emailDetails.getProviderTin(),
							Constants.RECORD_TYPE_RECON, Constants.FREQ_WEEKLY);
					List<RecordDetails> pendDetails = emailRepository.getRecordList(emailDetails.getProviderTin(),
							Constants.RECORD_TYPE_PEND, Constants.FREQ_WEEKLY);
					if (!reconDetails.isEmpty() || !pendDetails.isEmpty()) {

						sendRecord(reconDetails.size(), pendDetails.size(), Constants.MAIL_WEEKLY_SUBJECT,
								emailDetails.getReconEmailAddress(), Constants.RECORD_TYPE_RECON, Constants.FREQ_WEEKLY,
								emailDetails.getPrimaryEmailAddress(), emailDetails.getProviderName(), true);
					}
				}
			} else {
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
		} catch (MessagingException ex) {
			StringBuffer exception = new StringBuffer(ex.getMessage().toString());

			if (exception.indexOf("ConnectException") >= 0) {
				System.out.println(" Unable to Connect Mail server");
				throw new Exception();
			} else if (exception.indexOf("AddressException") >= 0) {
				try {
					sendMail(primaryEmailAddress,
							"Invalid Email address for " + recordType + " for Frequency " + frequency,
							getHTMLMessageForInvalidEmail(recordType, frequency));
				} catch (MessagingException e1) {
					System.out.println("Invalid Admin Email address");
				}
			} else {
				System.out.println("Email has not been sent.: " + exception.toString());

			}

		}
	}

	private String getHTMLMessageForInvalidEmail(String recordType, String frequency) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<!DOCTYPE html><html>");
		stringBuilder.append("<head></head><body> <p>Hello Trackit,</p></br> <p>");
		if (Constants.RECORD_TYPE_PEND.equals(recordType) && Constants.FREQ_DAILY.equals(frequency)) {
			stringBuilder.append(" Invalid Email Address provided for Recon tickets for Daily,");
		} else if (Constants.RECORD_TYPE_RECON.equals(recordType) && Constants.FREQ_DAILY.equals(frequency)) {
			stringBuilder.append(" Invalid Email Address provided for Pended tickets for Daily,");
		} else if (Constants.RECORD_TYPE_PEND.equals(recordType) && Constants.FREQ_WEEKLY.equals(frequency)) {
			stringBuilder.append(" Invalid Email Address provided for Recon tickets for Weekly,");
		} else if (Constants.RECORD_TYPE_RECON.equals(recordType) && Constants.FREQ_WEEKLY.equals(frequency)) {
			stringBuilder.append(" Invalid Email Address provided for Pended tickets for Weekly,");
		}
		stringBuilder.append("</body></html>");
		return stringBuilder.toString();
	}

	private String getHTMLMessage(int reconCount, int pendCount, String recordType, String frequency, String name,
			boolean isSameEmailAddress) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<!DOCTYPE html><html>");
		stringBuilder.append("<head></head><body> <p>Hello  ");
		stringBuilder.append(name);
		stringBuilder.append(",</p> <p> You have ");
		if (!isSameEmailAddress) {
			if (Constants.RECORD_TYPE_RECON.equals(recordType) && Constants.FREQ_DAILY.equals(frequency)) {
				stringBuilder.append(reconCount);
				stringBuilder.append(" Reconsideration tickets that have been update in last 24hrs,");
			} else if (Constants.RECORD_TYPE_PEND.equals(recordType) && Constants.FREQ_DAILY.equals(frequency)) {
				stringBuilder.append(pendCount);
				stringBuilder.append(" Pended tickets that have been update in last 24hrs,");
			} else if (Constants.RECORD_TYPE_RECON.equals(recordType) && Constants.FREQ_WEEKLY.equals(frequency)) {
				stringBuilder.append(reconCount);
				stringBuilder.append(" Reconsideration tickets that have been update in last week,");
			} else if (Constants.RECORD_TYPE_PEND.equals(recordType) && Constants.FREQ_WEEKLY.equals(frequency)) {
				stringBuilder.append(pendCount);
				stringBuilder.append(" Pended tickets that have been update in last week,");
			}
		} else {
			if (Constants.FREQ_DAILY.equals(frequency)) {
				stringBuilder.append(reconCount);
				stringBuilder.append(" Reconsideration tickets that have been update in last 24hrs,");
				stringBuilder.append("</br><br> You have ");
				stringBuilder.append(pendCount);
				stringBuilder.append(" Pended tickets that have been update in last 24hrs,");
			} else if (Constants.FREQ_WEEKLY.equals(frequency)) {
				stringBuilder.append(reconCount);
				stringBuilder.append(" Reconsideration tickets that have been update in last week,");
				stringBuilder.append("</br> You have ");
				stringBuilder.append(pendCount);
				stringBuilder.append(" Pended tickets that have been update in last week,");
			}
		}
		stringBuilder.append(
				" To view those update please <a href=\"https://www.google.com\" target=\"_blank\">click here</a>");
		stringBuilder.append(
				"For help using Trackit, refer to the quick reference guide or sign up for training webinar</p>");
		stringBuilder.append("</body></html>");
		return stringBuilder.toString();
	}

	// /**
	// * Hello Joe Dane You have 10 reconsideration tickets, that need for the
	// review.
	// * You have 8 Pended tickets, that need for the review, Please click here. For
	// * Immediate Assistance please call 800-232-4545 in the Eastern time 8am to 10
	// * pm.
	// *
	// * Thanks UnitedHealthcare
	// *
	// * @param count
	// * @param frequency
	// * @param name
	// * @return
	// */
	// private String getHTMLMessageForRecAndPend(int reconCount, int pendCount,
	// String frequency, String name) {
	// StringBuilder stringBuilder = new StringBuilder();
	// stringBuilder.append("<!DOCTYPE html><html>");
	// stringBuilder.append("<head></head><body> <p>Hello ");
	// stringBuilder.append(name);
	// stringBuilder.append(",</p> <p> You have ");
	//
	// if (Constants.FREQ_DAILY.equals(frequency)) {
	// stringBuilder.append(reconCount);
	// stringBuilder.append(" Reconsideration tickets that have been update in last
	// 24hrs,");
	// stringBuilder.append("</br> You have ");
	// stringBuilder.append(pendCount);
	// stringBuilder.append(" Pended tickets that have been update in last 24hrs,");
	// } else if (Constants.FREQ_WEEKLY.equals(frequency)) {
	// stringBuilder.append(reconCount);
	// stringBuilder.append(" Reconsideration tickets that have been update in last
	// week,");
	// stringBuilder.append("</br> You have ");
	// stringBuilder.append(pendCount);
	// stringBuilder.append(" Pended tickets that have been update in last week,");
	// }
	// stringBuilder.append(
	// " To view those update please <a href=\"https://www.google.com\"
	// target=\"_blank\">click here</a>");
	// stringBuilder.append(
	// "For help using Trackit, refer to the quick reference guide or sign up for
	// training webinar</p>");
	// stringBuilder.append("</body></html>");
	// return stringBuilder.toString();
	// }

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

}
