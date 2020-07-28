package com.email.api.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.email.api.model.AuditEmailDetails;
import com.email.api.model.EmailDetails;
import com.email.api.model.RecordDetails;
import com.email.api.model.RecordsCount;
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

	private List<Error> validationList;

	@Override
	public void sendDailyEmail(EmailDetails emailDetails) throws Exception {

		List<EmailDetails> details = new ArrayList<>();

		if (LocalDate.isMondayToday()) {
			details = emailRepository.getEmailDetails(null);
		} else {
			details = emailRepository.getEmailDetails(Constants.FREQ_DAILY);
		}

		List<String> primaryEmailAddressList = new ArrayList<String>();
		for (EmailDetails emailDetails1 : details) {
			if (!primaryEmailAddressList.contains(emailDetails1.getPrimaryEmailAddress())) {
				primaryEmailAddressList.add(emailDetails1.getPrimaryEmailAddress());
				List<EmailDetails> detailsV1 = new ArrayList<EmailDetails>();
				detailsV1 = emailRepository.getDetailsWithPrimaryEmailAddress(emailDetails1.getPrimaryEmailAddress());
				Map<String, List<RecordsCount>> recordsMap = new HashMap<String, List<RecordsCount>>();

				for (EmailDetails primaryEmailDetail1 : detailsV1) {

					Map<String, RecordsCount> map = getCountForRecordOld(primaryEmailDetail1);

					for (Entry<String, RecordsCount> recordCountMap : map.entrySet()) {
						String emailAddress = recordCountMap.getKey();
						RecordsCount recordsCounts = recordCountMap.getValue();
						if (recordsMap.containsKey(emailAddress)) {
							List<RecordsCount> list = recordsMap.get(emailAddress);
							list.add(recordsCounts);
							recordsMap.put(emailAddress, list);
						} else {
							List<RecordsCount> list = new ArrayList<RecordsCount>();
							list.add(recordsCounts);
							recordsMap.put(emailAddress, list);
						}
					}
				}
				sendRecodEmailDetailsnew(recordsMap);
			}
		}

	}

	@Override
	public EmailDetails getProviderDetails(String corporateTaxID, String providerTin, String uuID) {
		return emailRepository.getProviderDetails(corporateTaxID, providerTin, uuID);
	}

	public Map<String, RecordsCount> getCountForRecordOld(EmailDetails emailDetails) {

		Map<String, RecordsCount> mapCount = new HashMap<String, RecordsCount>();
		RecordsCount count2 = new RecordsCount();

		if (isValidType(emailDetails.getReconAlert())) {
			List<RecordDetails> reconRecordDetails = emailRepository.getRecordList(emailDetails.getProviderTin(),
					Constants.RECORD_TYPE_RECON, Constants.FREQ_DAILY);
			int recordCount = 0;
			for (RecordDetails recordDetails : reconRecordDetails) {
				if (null != emailDetails.getReconEmailAddress()
						&& null != recordDetails.getProviderDetails().getOperatorEmailAddress()
						&& null != emailDetails.getPrimaryEmailAddress()) {
					if (emailDetails.getReconEmailAddress()
							.equals(recordDetails.getProviderDetails().getOperatorEmailAddress())
							|| emailDetails.getPrimaryEmailAddress()
									.equals(recordDetails.getProviderDetails().getOperatorEmailAddress())) {
						recordCount++;
					}
				}
			}
			if (recordCount > 0) {
				count2.setReconFrequency(emailDetails.getReconFrequency());
				count2.setCorporateTaxID(emailDetails.getCorporateTaxID());
				count2.setProviderTin(emailDetails.getProviderTin());
				count2.setUuID(emailDetails.getUuID());
//				count2.setProviderName(emailDetails.getProviderName());
				count2.setReconCount(recordCount);
				count2.setProviderOrganization(emailDetails.getProviderName());
				mapCount.put(emailDetails.getReconEmailAddress(), count2);
			}
		}
		if (isValidType(emailDetails.getPendAlert())) {
			List<RecordDetails> recordDetails = emailRepository.getRecordList(emailDetails.getProviderTin(),
					Constants.RECORD_TYPE_PEND, Constants.FREQ_DAILY);
			int pendRecordCount = 0;
			for (RecordDetails recordDetails2 : recordDetails) {
				if (emailDetails.getReconEmailAddress()
						.equals(recordDetails2.getProviderDetails().getOperatorEmailAddress())
						|| emailDetails.getPrimaryEmailAddress()
								.equals(recordDetails2.getProviderDetails().getOperatorEmailAddress())) {
					pendRecordCount++;
				}
			}
			if (pendRecordCount > 0) {
				if (mapCount.containsKey(emailDetails.getPendEmailAddress())) {
					RecordsCount coun = mapCount.get(emailDetails.getPendEmailAddress());
					coun.setPendFrequency(emailDetails.getPendFrequency());
					coun.setCorporateTaxID(emailDetails.getCorporateTaxID());
					coun.setProviderTin(emailDetails.getProviderTin());
					coun.setUuID(emailDetails.getUuID());
//					coun.setProviderName(emailDetails.getProviderName());
					coun.setProviderOrganization(emailDetails.getProviderName());
					coun.setPendCount(pendRecordCount);
				} else {
					RecordsCount count = new RecordsCount();
					count.setPendFrequency(emailDetails.getPendFrequency());
					count.setReconFrequency(emailDetails.getReconFrequency());
					count.setCorporateTaxID(emailDetails.getCorporateTaxID());
					count.setProviderTin(emailDetails.getProviderTin());
//					count.setProviderName(emailDetails.getProviderName());
					count.setProviderOrganization(emailDetails.getProviderName());
					count.setPendCount(1);
					mapCount.put(emailDetails.getPendEmailAddress(), count);

				}
			}

		}
		if (LocalDate.isMondayToday()) {
			if (isValidType(emailDetails.getReconAlert())) {
				List<RecordDetails> recordDetails = emailRepository.getRecordList(emailDetails.getProviderTin(),
						Constants.RECORD_TYPE_RECON, Constants.FREQ_WEEKLY);
				int recordCount = 0;
				for (RecordDetails recordDetails2 : recordDetails) {
					if (emailDetails.getReconEmailAddress()
							.equals(recordDetails2.getProviderDetails().getOperatorEmailAddress())
							|| emailDetails.getPrimaryEmailAddress()
									.equals(recordDetails2.getProviderDetails().getOperatorEmailAddress())) {
						recordCount++;
					}
				}
				if (recordCount > 0) {
					if (mapCount.containsKey(emailDetails.getReconEmailAddress())) {
						RecordsCount coun = mapCount.get(emailDetails.getReconEmailAddress());
						coun.setReconFrequency(emailDetails.getReconFrequency());
						coun.setCorporateTaxID(emailDetails.getCorporateTaxID());
						coun.setProviderTin(emailDetails.getProviderTin());
						coun.setReconCount(recordDetails.size());
					} else {
						RecordsCount count = new RecordsCount();
						count.setReconFrequency(emailDetails.getReconFrequency());
						count.setCorporateTaxID(emailDetails.getCorporateTaxID());
						count.setProviderTin(emailDetails.getProviderTin());
						count.setReconCount(recordDetails.size());
						mapCount.put(emailDetails.getReconEmailAddress(), count);

					}
				}

			}
			if (isValidType(emailDetails.getPendAlert())) {
				List<RecordDetails> recordDetails = emailRepository.getRecordList(emailDetails.getProviderTin(),
						Constants.RECORD_TYPE_PEND, Constants.FREQ_WEEKLY);
				int pendRecordCount = 0;
				for (RecordDetails recordDetails2 : recordDetails) {
					if (emailDetails.getReconEmailAddress()
							.equals(recordDetails2.getProviderDetails().getOperatorEmailAddress())
							|| emailDetails.getPrimaryEmailAddress()
									.equals(recordDetails2.getProviderDetails().getOperatorEmailAddress())) {
						pendRecordCount++;
					}
				}
				if (pendRecordCount > 0) {
					if (mapCount.containsKey(emailDetails.getPendEmailAddress())) {
						RecordsCount coun = mapCount.get(emailDetails.getPendEmailAddress());
						coun.setPendFrequency(emailDetails.getPendFrequency());
						coun.setCorporateTaxID(emailDetails.getCorporateTaxID());
						coun.setProviderTin(emailDetails.getProviderTin());
						coun.setPendCount(recordDetails.size());
					} else {
						RecordsCount count = new RecordsCount();
						count.setPendFrequency(emailDetails.getPendFrequency());
						count.setCorporateTaxID(emailDetails.getCorporateTaxID());
						count.setProviderTin(emailDetails.getProviderTin());
						count.setPendCount(1);
						mapCount.put(emailDetails.getPendEmailAddress(), count);

					}
				}

			}
		}

		return mapCount;
	}

	private boolean isValidType(Boolean alert) {
		if ((alert != null && alert)) {
			return true;
		}
		return false;

	}

	private void sendRecodEmailDetailsnew(Map<String, List<RecordsCount>> mapList) throws Exception {
		for (Entry<String, List<RecordsCount>> recordCountMap : mapList.entrySet()) {
			String emailAddress = recordCountMap.getKey();
			List<RecordsCount> recordsCounts = recordCountMap.getValue();
			try {
				String text = getHTMLMessage(recordsCounts);
				sendMail(emailAddress, text);
//				for (RecordsCount recordsCount : recordsCounts) {
//					AuditEmailDetails auditEmailDeteals = convertToAudit(recordsCount, emailAddress);
//					emailRepository.saveAudit(auditEmailDeteals);
//				}
			} catch (Exception ex) {
				throw ex;
			}
		}
	}

	private String getHTMLMessage(List<RecordsCount> recordCountList) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<!DOCTYPE html><html>");
		stringBuilder.append("<head></head><body> <p>Hello  ");
//		String name = recordCountList.get(0).getProviderName();
//		if (null != name) {
//			stringBuilder.append(name);
//		} else {
		stringBuilder.append("Care Provider");
//		}
		stringBuilder.append("</p> <p>");
		stringBuilder.append("<table >");
		stringBuilder.append("<tr>");
		stringBuilder.append(" <th>Corporate Tax ID</th>");
		stringBuilder.append("<th>Provider Tin</th>");
		stringBuilder.append(" <th>Recod Type</th>");
		stringBuilder.append(" <th>Recod Count</th>");
		stringBuilder.append(" <th>Frequency</th>");
		stringBuilder.append(" <th>Provider Organization/Name</th>");
		stringBuilder.append("</tr>");
		boolean isPend = false;
		for (RecordsCount recordsCount : recordCountList) {

			if (recordsCount.getReconCount() > 0 && Constants.FREQ_DAILY.equals(recordsCount.getReconFrequency())) {
				stringBuilder.append("<tr>");
				stringBuilder.append(" <th>" + recordsCount.getCorporateTaxID() + "</th>");
				stringBuilder.append(" <th>" + recordsCount.getProviderTin() + "</th>");
				stringBuilder.append(" <th>" + Constants.RECORD_TYPE_RECON + "</th>");
				stringBuilder.append(" <th>" + recordsCount.getReconCount() + "</th>");
				stringBuilder.append(" <th>" + recordsCount.getReconFrequency() + "</th>");
				stringBuilder.append(" <th>" + recordsCount.getProviderOrganization() + "</th>");
				stringBuilder.append("</tr>");
			}
			if (recordsCount.getWeeklyReconCount() > 0
					&& Constants.FREQ_WEEKLY.equals(recordsCount.getReconFrequency())) {
				stringBuilder.append(",</p> <p> You have ");
				stringBuilder.append(recordsCount.getWeeklyReconCount());
				stringBuilder.append(" Reconsideration tickets that have been update in last week,");
			}

			if (recordsCount.getPendCount() > 0 && Constants.FREQ_DAILY.equals(recordsCount.getPendFrequency())) {
				stringBuilder.append("<tr>");
				stringBuilder.append(" <th>" + recordsCount.getCorporateTaxID() + "</th>");
				stringBuilder.append(" <th>" + recordsCount.getProviderTin() + "</th>");
				stringBuilder.append(" <th>" + Constants.RECORD_TYPE_PEND + "</th>");
				stringBuilder.append(" <th>" + recordsCount.getPendCount() + "</th>");
				stringBuilder.append(" <th>" + recordsCount.getPendFrequency() + "</th>");
				stringBuilder.append(" <th>" + recordsCount.getProviderOrganization() + "</th>");
				stringBuilder.append("</tr>");
				isPend = true;
			}

			if (recordsCount.getWeeklyPendCount() > 0
					&& Constants.FREQ_WEEKLY.equals(recordsCount.getPendFrequency())) {
				stringBuilder.append("</p> You have ");
				stringBuilder.append(recordsCount.getWeeklyPendCount());
				stringBuilder.append(" Pended tickets that have been update in last week,");
			}
		}
		stringBuilder.append("</table >");
		if (isPend) {
			stringBuilder.append("<br>Pended claims are very important.");
		}
		stringBuilder.append(
				" To view those update please <a href=\"https://www.google.com\" target=\"_blank\">click here</a>");
		stringBuilder.append(
				"For help using Trackit, refer to the quick reference guide or sign up for training webinar</p>");
		stringBuilder.append("</body></html>");
		System.out.println(stringBuilder.toString());
		return stringBuilder.toString();
	}

	private void sendMail(String to, String text) throws MessagingException, UnsupportedEncodingException {
		MimeMessage mail = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail, true, "UTF-8");
		helper.setFrom("venkatsai0830@gmail.com", "Trackit");
		helper.setTo(to);
		helper.setSubject("Notification of TrackIt");
		helper.setText(text, true);
		javaMailSender.send(mail);
		System.out.println("sent Email: " + to);
	}

//	private AuditEmailDetails convertToAudit(RecordsCount recordsCount, String emailAddress) {
//		AuditEmailDetails auditEmailDeteals = new AuditEmailDetails();
//		auditEmailDeteals.setCorporateTaxID(recordsCount.getCorporateTaxID());
//		auditEmailDeteals.setEmailAddress(emailAddress);
//		auditEmailDeteals.setPendCount(recordsCount.getPendCount());
//		auditEmailDeteals.setReconCount(recordsCount.getReconCount());
//		auditEmailDeteals.setPendFrequency(recordsCount.getPendFrequency());
//		auditEmailDeteals.setReconFrequency(recordsCount.getReconFrequency());
//		auditEmailDeteals.setProviderTin(recordsCount.getProviderTin());
//		auditEmailDeteals.setUuID(recordsCount.getUuID());
//		auditEmailDeteals.setCreatedDate(LocalDate.getCDT());
//		return auditEmailDeteals;
//	}

//	@Override
//	public List<AuditEmailDetails> getAuditEmailDetails(String corporateTaxID, String providerTin, String uuID) {
//		return emailRepository.getAuditEmailDetails(corporateTaxID, providerTin, uuID);
//	}
}
