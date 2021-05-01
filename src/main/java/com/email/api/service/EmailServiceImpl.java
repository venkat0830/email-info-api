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
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.email.api.model.AuditEmailDetails;
import com.email.api.model.BoTable;
import com.email.api.model.EmailDetails;
import com.email.api.model.RecordDetails;
import com.email.api.model.RecordsCount;
//import com.email.api.repository.EmailRepository;
import com.email.api.repository.EmailRepositoryImpl;
import com.email.api.utilities.Constants;
import com.email.api.utilities.Error;
import com.email.api.utilities.LocalDate;

@Service
public class EmailServiceImpl {

	@Autowired
	EmailRepositoryImpl emailRepository;

	@Autowired
	JavaMailSender javaMailSender;

	public void setEmailRepository(EmailRepositoryImpl emailRepository) {
		this.emailRepository = emailRepository;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	private List<Error> validationList;
//	List<Map<String, String>> pharmaList = new ArrayList<Map<String, String>>();
//	Map<String, String> map = new HashMap<String, String>();
//	String providerTin;
//	String frequency;

//	@KafkaListener(topics = "recon-stage", groupId = "recon-group", containerFactory = "kafkaListenerContainerFactory")
//	private void receiveEmailDetails(String str) {
//		com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
//		try {
//			String emailAdd = mapper.readValue(str, String.class);
//			sendDailyEmail(emailAdd);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		System.out.println("Message Received:" + str);
//	}

	public void sendDailyEmail(String emailAdd) throws Exception {
		long startTime = System.currentTimeMillis();
		List<EmailDetails> detailsV1 = emailRepository.getDetailsWithPrimaryEmailAddress(emailAdd);
		long endTime = System.currentTimeMillis();
		System.out.println(
				"total Time Taken to get the List of Details:" + (endTime - startTime) + " size: " + detailsV1.size());

		Map<String, List<RecordsCount>> recordsMap = new HashMap<String, List<RecordsCount>>();

		detailsV1.parallelStream().forEach(emailDetailObject -> {
			try {
				Map<String, RecordsCount> map = getCountForRecordOld(emailDetailObject);
				map.forEach((emailAddress, recordsCounts) -> {
					if (recordsMap.containsKey(emailAddress)) {
						List<RecordsCount> list = recordsMap.get(emailAddress);
						list.add(recordsCounts);
						recordsMap.put(emailAddress, list);
					} else {
						List<RecordsCount> list = new ArrayList<RecordsCount>();
						list.add(recordsCounts);
						recordsMap.put(emailAddress, list);
					}
				});
			} catch (Exception ex) {
				System.out.println(
						"Exception encountered in the method sendDailyEmail in Class Service:" + ex.getMessage());
			}

		});
		sendRecodEmailDetailsnew(recordsMap);

//		pharmaList.forEach(map1 -> {
//			map1.entrySet().forEach(map2 -> {
//				providerTin = map2.getKey();
//				frequency = map2.getValue();
//				emailRepository.updatePhr(providerTin, frequency);
//			});
//		});
//		System.out.println("List of Map of PharmaProviderTin and Freq:" + pharmaList);
		System.out.println("TotalTime to send the email:" + (System.currentTimeMillis() - startTime));

	}

	public Map<String, RecordsCount> getCountForRecordOld(EmailDetails emailDetails) {

		Map<String, RecordsCount> mapCount = new HashMap<String, RecordsCount>();

		if (isValidType(emailDetails.getReconAlert()) && null != emailDetails.getReconEmailAddress()
				&& emailDetails.getReconFrequency().equalsIgnoreCase(Constants.FREQ_DAILY)) {

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
				RecordsCount count2 = new RecordsCount();
				count2.setReconFrequency(emailDetails.getReconFrequency());
				count2.setCorporateProviderMpin(emailDetails.getCorporateProviderMpin());
				count2.setProviderTin(emailDetails.getProviderTin());
				count2.setUuID(emailDetails.getUuID());
				count2.setReconCount(recordCount);
				count2.setProviderOrganization(emailDetails.getProviderName());
				if (emailDetails.getPrimaryEmailAddress().equals(emailDetails.getReconEmailAddress())) {
					mapCount.put(emailDetails.getPrimaryEmailAddress(), count2);
				} else {
					mapCount.put(emailDetails.getReconEmailAddress(), count2);
				}
			}
		}
		if (isValidType(emailDetails.getPendAlert()) && null != emailDetails.getPendEmailAddress()
				&& emailDetails.getPendFrequency().equalsIgnoreCase(Constants.FREQ_DAILY)) {
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
					coun.setCorporateProviderMpin(emailDetails.getCorporateProviderMpin());
					coun.setProviderTin(emailDetails.getProviderTin());
					coun.setUuID(emailDetails.getUuID());
					coun.setProviderOrganization(emailDetails.getProviderName());
					coun.setPendCount(pendRecordCount);
				} else {
					RecordsCount count = new RecordsCount();
					count.setPendFrequency(emailDetails.getPendFrequency());
					count.setCorporateProviderMpin(emailDetails.getCorporateProviderMpin());
					count.setProviderTin(emailDetails.getProviderTin());
					count.setProviderOrganization(emailDetails.getProviderName());
					count.setPendCount(pendRecordCount);
					mapCount.put(emailDetails.getPendEmailAddress(), count);
				}
			}

		}
		if (isValidType(emailDetails.getAppealsAlert()) && null != emailDetails.getAppealsEmailAddress()
				&& emailDetails.getAppealsFrequency().equalsIgnoreCase(Constants.FREQ_DAILY)) {
			List<RecordDetails> recordDetails = emailRepository.getRecordList(emailDetails.getProviderTin(), "APPEALS",
					Constants.FREQ_DAILY);
			if (!recordDetails.isEmpty()) {
				if (mapCount.containsKey(emailDetails.getAppealsEmailAddress())) {
					RecordsCount coun = mapCount.get(emailDetails.getAppealsEmailAddress());
					coun.setAppealsFrequency(emailDetails.getAppealsFrequency());
					coun.setCorporateProviderMpin(emailDetails.getCorporateProviderMpin());
					coun.setProviderTin(emailDetails.getProviderTin());
					coun.setUuID(emailDetails.getUuID());
					coun.setProviderOrganization(emailDetails.getProviderName());
					coun.setAppealsCount(recordDetails.size());
				} else {
					RecordsCount count = new RecordsCount();
					count.setAppealsFrequency(emailDetails.getAppealsFrequency());
					count.setCorporateProviderMpin(emailDetails.getCorporateProviderMpin());
					count.setProviderTin(emailDetails.getProviderTin());
					count.setProviderOrganization(emailDetails.getProviderName());
					count.setAppealsCount(recordDetails.size());
					mapCount.put(emailDetails.getAppealsEmailAddress(), count);
				}
			}

		}
		if (isValidType(emailDetails.getPharmaCouponAlert()) && null != emailDetails.getPharmaCouponEmailAddress()
				&& emailDetails.getPharmaCouponFrequency().equalsIgnoreCase(Constants.FREQ_DAILY)) {
			List<RecordDetails> recordDetails = emailRepository.getRecordList(emailDetails.getProviderTin(),
					"PHARMACOUPON", Constants.FREQ_DAILY);
			if (!recordDetails.isEmpty()) {
				if (mapCount.containsKey(emailDetails.getPharmaCouponEmailAddress())) {
					RecordsCount coun = mapCount.get(emailDetails.getPharmaCouponEmailAddress());
					coun.setPharmaCouponFrequency(emailDetails.getPharmaCouponFrequency());
					coun.setCorporateProviderMpin(emailDetails.getCorporateProviderMpin());
					coun.setProviderTin(emailDetails.getProviderTin());
					coun.setUuID(emailDetails.getUuID());
					coun.setProviderOrganization(emailDetails.getProviderName());
					coun.setPharmaCouponCount(recordDetails.size());
				} else {
					RecordsCount count = new RecordsCount();
					count.setPharmaCouponFrequency(emailDetails.getPharmaCouponFrequency());
					count.setCorporateProviderMpin(emailDetails.getCorporateProviderMpin());
					count.setProviderTin(emailDetails.getProviderTin());
					count.setProviderOrganization(emailDetails.getProviderName());
					count.setPharmaCouponCount(recordDetails.size());
					mapCount.put(emailDetails.getPharmaCouponEmailAddress(), count);
				}
			}
		}
		if (LocalDate.isMondayToday()) {
			if (isValidType(emailDetails.getReconAlert()) && emailDetails.getReconEmailAddress() != null
					&& emailDetails.getReconFrequency().equalsIgnoreCase(Constants.FREQ_WEEKLY)) {
				List<RecordDetails> reconRecordDetails = emailRepository.getRecordList(emailDetails.getProviderTin(),
						Constants.RECORD_TYPE_RECON, Constants.FREQ_WEEKLY);
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
					if (mapCount.containsKey(emailDetails.getReconEmailAddress())) {
						RecordsCount coun = mapCount.get(emailDetails.getReconEmailAddress());
						coun.setReconFrequency(emailDetails.getReconFrequency());
						coun.setCorporateProviderMpin(emailDetails.getCorporateProviderMpin());
						coun.setProviderTin(emailDetails.getProviderTin());
						coun.setProviderOrganization(emailDetails.getProviderName());
						coun.setWeeklyReconCount(recordCount);
					} else {
						RecordsCount count = new RecordsCount();
						count.setReconFrequency(emailDetails.getReconFrequency());
						count.setCorporateProviderMpin(emailDetails.getCorporateProviderMpin());
						count.setProviderTin(emailDetails.getProviderTin());
						count.setWeeklyReconCount(recordCount);
						count.setProviderOrganization(emailDetails.getProviderName());
						mapCount.put(emailDetails.getReconEmailAddress(), count);

					}
				}

			}
			if (isValidType(emailDetails.getPendAlert()) && null != emailDetails.getAppealsEmailAddress()
					&& emailDetails.getAppealsFrequency().equalsIgnoreCase(Constants.FREQ_WEEKLY)) {
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
						coun.setCorporateProviderMpin(emailDetails.getCorporateProviderMpin());
						coun.setProviderTin(emailDetails.getProviderTin());
						coun.setProviderOrganization(emailDetails.getProviderName());
						coun.setWeeklyPendCount(pendRecordCount);
					} else {
						RecordsCount count = new RecordsCount();
						count.setPendFrequency(emailDetails.getPendFrequency());
						count.setCorporateProviderMpin(emailDetails.getCorporateProviderMpin());
						count.setProviderTin(emailDetails.getProviderTin());
						count.setWeeklyPendCount(pendRecordCount);
						count.setProviderOrganization(emailDetails.getProviderName());
						mapCount.put(emailDetails.getPendEmailAddress(), count);

					}
				}

			}

			if (isValidType(emailDetails.getAppealsAlert()) && emailDetails.getAppealsEmailAddress() != null
					&& emailDetails.getAppealsFrequency().equalsIgnoreCase(Constants.FREQ_WEEKLY)) {
				List<RecordDetails> recordDetails = emailRepository.getRecordList(emailDetails.getProviderTin(),
						"APPEALS", Constants.FREQ_WEEKLY);
				if (!recordDetails.isEmpty()) {
					if (mapCount.containsKey(emailDetails.getAppealsEmailAddress())) {
						RecordsCount coun = mapCount.get(emailDetails.getAppealsEmailAddress());
						coun.setAppealsFrequency(emailDetails.getAppealsFrequency());
						coun.setCorporateProviderMpin(emailDetails.getCorporateProviderMpin());
						coun.setProviderTin(emailDetails.getProviderTin());
						coun.setUuID(emailDetails.getUuID());
						coun.setProviderOrganization(emailDetails.getProviderName());
						coun.setWeeklyAppealsCount(recordDetails.size());
					} else {
						RecordsCount count = new RecordsCount();
						count.setAppealsFrequency(emailDetails.getAppealsFrequency());
						count.setCorporateProviderMpin(emailDetails.getCorporateProviderMpin());
						count.setProviderTin(emailDetails.getProviderTin());
						count.setProviderOrganization(emailDetails.getProviderName());
						count.setWeeklyAppealsCount(recordDetails.size());
						mapCount.put(emailDetails.getAppealsEmailAddress(), count);
					}
				}

			}

			if (isValidType(emailDetails.getPharmaCouponAlert()) && emailDetails.getPharmaCouponEmailAddress() != null
					&& emailDetails.getPharmaCouponFrequency().equalsIgnoreCase(Constants.FREQ_WEEKLY)) {
				List<RecordDetails> recordDetails = emailRepository.getRecordList(emailDetails.getProviderTin(),
						"PHARMACOUPON", Constants.FREQ_WEEKLY);
				if (!recordDetails.isEmpty()) {
					if (mapCount.containsKey(emailDetails.getPharmaCouponEmailAddress())) {
						RecordsCount coun = mapCount.get(emailDetails.getPharmaCouponEmailAddress());
						coun.setPharmaCouponFrequency(emailDetails.getPharmaCouponFrequency());
						coun.setCorporateProviderMpin(emailDetails.getCorporateProviderMpin());
						coun.setProviderTin(emailDetails.getProviderTin());
						coun.setUuID(emailDetails.getUuID());
						coun.setProviderOrganization(emailDetails.getProviderName());
						coun.setWpharmaCouponCount(recordDetails.size());
					} else {
						RecordsCount count = new RecordsCount();
						count.setPharmaCouponFrequency(emailDetails.getPharmaCouponFrequency());
						count.setCorporateProviderMpin(emailDetails.getCorporateProviderMpin());
						count.setProviderTin(emailDetails.getProviderTin());
						count.setProviderOrganization(emailDetails.getProviderName());
						count.setWpharmaCouponCount(recordDetails.size());
						mapCount.put(emailDetails.getPharmaCouponEmailAddress(), count);
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
		if (!mapList.isEmpty()) {
			mapList.forEach((emailAddress, detailsOfRecords) -> {
				try {
					String text = getHTMLMessage(detailsOfRecords);
					sendMail(emailAddress, text);
					detailsOfRecords.parallelStream().forEach(recordsCount -> {
						AuditEmailDetails auditEmailDeteals = convertToAudit(recordsCount, emailAddress);
						emailRepository.saveAudit(auditEmailDeteals);
						if (auditEmailDeteals.getDailyPharmaCount() > 0
								|| auditEmailDeteals.getWeeklyPharmaCount() > 0) {
							String providerTin = auditEmailDeteals.getProviderTin();
							String pharmaFre = auditEmailDeteals.getPharmaFrequency();
							System.out.println("Pharma ProviderTin" + providerTin + " and fre" + pharmaFre);
							emailRepository.updatePhr(providerTin, pharmaFre);
						}
					});
				} catch (Exception ex) {
					System.out.println("Exception Encountered:" + ex.getMessage());
				}
			});
		} else {
			System.out.println("MapList is empty, no need to call the email notification part.");
		}

	}

	private String getHTMLMessage(List<RecordsCount> recordCountList) {
		StringBuilder stringBuilder = new StringBuilder();
		if (!recordCountList.isEmpty()) {
			stringBuilder.append("<!DOCTYPE html><html>");
			stringBuilder.append("<head></head><body> <p>Hello  ");
			stringBuilder.append("Care Provider");
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
					stringBuilder.append(" <th>" + recordsCount.getCorporateProviderMpin() + "</th>");

					stringBuilder.append(" <th>" + recordsCount.getProviderTin() + "</th>");
					stringBuilder.append(" <th>" + Constants.RECORD_TYPE_RECON + "</th>");
					stringBuilder.append(" <th>" + recordsCount.getReconCount() + "</th>");
					stringBuilder.append(" <th>" + recordsCount.getReconFrequency() + "</th>");
					stringBuilder.append(" <th>" + recordsCount.getProviderOrganization() + "</th>");
					stringBuilder.append("</tr>");
				}
				if (recordsCount.getWeeklyReconCount() > 0
						&& Constants.FREQ_WEEKLY.equals(recordsCount.getReconFrequency())) {
					stringBuilder.append("<tr>");
					stringBuilder.append(" <th>" + recordsCount.getCorporateProviderMpin() + "</th>");

					stringBuilder.append(" <th>" + recordsCount.getProviderTin() + "</th>");
					stringBuilder.append(" <th>" + Constants.RECORD_TYPE_RECON + "</th>");
					stringBuilder.append(" <th>" + recordsCount.getWeeklyReconCount() + "</th>");
					stringBuilder.append(" <th>" + recordsCount.getReconFrequency() + "</th>");
					stringBuilder.append(" <th>" + recordsCount.getProviderOrganization() + "</th>");
					stringBuilder.append("</tr>");
				}

				if (recordsCount.getPendCount() > 0 && Constants.FREQ_DAILY.equals(recordsCount.getPendFrequency())) {
					stringBuilder.append("<tr>");
					stringBuilder.append(" <th>" + recordsCount.getCorporateProviderMpin() + "</th>");

					stringBuilder.append(" <th>" + recordsCount.getProviderTin() + "</th>");
					stringBuilder.append(" <th>" + Constants.RECORD_TYPE_PEND + "</th>");
					stringBuilder.append(" <th>" + recordsCount.getPendCount() + "</th>");
					stringBuilder.append(" <th>" + recordsCount.getPendFrequency() + "</th>");
					stringBuilder.append(" <th>" + recordsCount.getProviderOrganization() + "</th>");
					stringBuilder.append("</tr>");
				}

				if (recordsCount.getWeeklyPendCount() > 0
						&& Constants.FREQ_WEEKLY.equals(recordsCount.getPendFrequency())) {
					stringBuilder.append("<tr>");
					stringBuilder.append(" <th>" + recordsCount.getCorporateProviderMpin() + "</th>");

					stringBuilder.append(" <th>" + recordsCount.getProviderTin() + "</th>");
					stringBuilder.append(" <th>" + Constants.RECORD_TYPE_PEND + "</th>");
					stringBuilder.append(" <th>" + recordsCount.getWeeklyPendCount() + "</th>");
					stringBuilder.append(" <th>" + recordsCount.getPendFrequency() + "</th>");
					stringBuilder.append(" <th>" + recordsCount.getProviderOrganization() + "</th>");
					stringBuilder.append("</tr>");
				}
				if (recordsCount.getAppealsCount() > 0
						&& Constants.FREQ_DAILY.equals(recordsCount.getAppealsFrequency())) {
					stringBuilder.append("<tr>");
					stringBuilder.append(" <th>" + recordsCount.getCorporateProviderMpin() + "</th>");
					stringBuilder.append(" <th>" + recordsCount.getProviderTin() + "</th>");
					stringBuilder.append(" <th>" + "APPEALS" + "</th>");
					stringBuilder.append(" <th>" + recordsCount.getAppealsCount() + "</th>");
					stringBuilder.append(" <th>" + recordsCount.getAppealsFrequency() + "</th>");
					stringBuilder.append(" <th>" + recordsCount.getProviderOrganization() + "</th>");
					stringBuilder.append("</tr>");
				}
				if (recordsCount.getWeeklyAppealsCount() > 0
						&& Constants.FREQ_WEEKLY.equals(recordsCount.getAppealsFrequency())) {
					stringBuilder.append("<tr>");
					stringBuilder.append(" <th>" + recordsCount.getCorporateProviderMpin() + "</th>");
					stringBuilder.append(" <th>" + recordsCount.getProviderTin() + "</th>");
					stringBuilder.append(" <th>" + "APPEALS" + "</th>");
					stringBuilder.append(" <th>" + recordsCount.getWeeklyAppealsCount() + "</th>");
					stringBuilder.append(" <th>" + recordsCount.getAppealsFrequency() + "</th>");
					stringBuilder.append(" <th>" + recordsCount.getProviderOrganization() + "</th>");
					stringBuilder.append("</tr>");
				}
				if (recordsCount.getPharmaCouponCount() > 0
						&& Constants.FREQ_DAILY.equals(recordsCount.getPharmaCouponFrequency())) {
					stringBuilder.append("<tr>");
					stringBuilder.append(" <th>" + recordsCount.getCorporateProviderMpin() + "</th>");
					stringBuilder.append(" <th>" + recordsCount.getProviderTin() + "</th>");
					stringBuilder.append(" <th>" + "PHARMACOUPON" + "</th>");
					stringBuilder.append(" <th>" + recordsCount.getPharmaCouponCount() + "</th>");
					stringBuilder.append(" <th>" + recordsCount.getPharmaCouponFrequency() + "</th>");
					stringBuilder.append(" <th>" + recordsCount.getProviderOrganization() + "</th>");
					stringBuilder.append("</tr>");

				}
				if (recordsCount.getWpharmaCouponCount() > 0
						&& Constants.FREQ_WEEKLY.equals(recordsCount.getPharmaCouponFrequency())) {
					stringBuilder.append("<tr>");
					stringBuilder.append(" <th>" + recordsCount.getCorporateProviderMpin() + "</th>");
					stringBuilder.append(" <th>" + recordsCount.getProviderTin() + "</th>");
					stringBuilder.append(" <th>" + "PHARMACOUPON" + "</th>");
					stringBuilder.append(" <th>" + recordsCount.getWpharmaCouponCount() + "</th>");
					stringBuilder.append(" <th>" + recordsCount.getPharmaCouponFrequency() + "</th>");
					stringBuilder.append(" <th>" + recordsCount.getProviderOrganization() + "</th>");
					stringBuilder.append("</tr>");

				}
			}
			stringBuilder.append("</table >");
			stringBuilder.append(
					" To view those update please <a href=\"https://www.google.com\" target=\"_blank\">click here</a>");
			stringBuilder.append(
					"For help using Trackit, refer to the quick reference guide or sign up for training webinar</p>");
			stringBuilder.append("</body></html>");
			System.out.println(stringBuilder.toString());
		}
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

	private AuditEmailDetails convertToAudit(RecordsCount recordsCount, String emailAddress) {
		AuditEmailDetails auditEmailDeteals = new AuditEmailDetails();
		auditEmailDeteals.setCorporateTaxID(recordsCount.getCorporateProviderMpin());
		auditEmailDeteals.setEmailAddress(emailAddress);
		auditEmailDeteals.setPendCount(recordsCount.getPendCount());
		auditEmailDeteals.setReconCount(recordsCount.getReconCount());
		auditEmailDeteals.setPendFrequency(recordsCount.getPendFrequency());
		auditEmailDeteals.setReconFrequency(recordsCount.getReconFrequency());
		auditEmailDeteals.setProviderTin(recordsCount.getProviderTin());
		auditEmailDeteals.setUuID(recordsCount.getUuID());
		auditEmailDeteals.setCreatedDate(LocalDate.getCDT());
		auditEmailDeteals.setPharmaFrequency(recordsCount.getPharmaCouponFrequency());
		auditEmailDeteals.setDailyPharmaCount(recordsCount.getPharmaCouponCount());
		auditEmailDeteals.setWeeklyPharmaCount(recordsCount.getWpharmaCouponCount());
		return auditEmailDeteals;
	}

	public List<AuditEmailDetails> getAuditEmailDetails(String emailAddress, String providerTin, String uuID) {
		return emailRepository.getAuditEmailDetails(emailAddress, providerTin, uuID);
	}

	public Map<String, Integer> getAuditSummary(String emailAddress, String providerTin, String uuID) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int totalDailyReconCount = 0;
		int totalDailyPendCount = 0;
		List<AuditEmailDetails> details = emailRepository.getAuditEmailDetails(emailAddress, providerTin, uuID);
		for (AuditEmailDetails reAuditEmailDetails : details) {
			totalDailyReconCount = totalDailyReconCount + reAuditEmailDetails.getReconCount();
			totalDailyPendCount = totalDailyPendCount + reAuditEmailDetails.getPendCount();
		}
		map.put("DailyRecon", totalDailyReconCount);
		map.put("DailyPend", totalDailyPendCount);
		return map;
	}
}
