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

		Map<String, RecordsCount> map = null;
		List<Map<String, RecordsCount>> mapList = new ArrayList<Map<String, RecordsCount>>();

		for (EmailDetails emailDetail : details) {
			map = getCountForRecordOld(emailDetail);
			mapList.add(map);
		}
		Map<String, List<RecordsCount>> recordsMap = new HashMap<String, List<RecordsCount>>();
		for (Map<String, RecordsCount> map2 : mapList) {
			if (recordsMap.isEmpty()) {
				List<RecordsCount> list = new ArrayList<RecordsCount>();
				for (Entry<String, RecordsCount> recordCountMap : map2.entrySet()) {
					String emailAddress = recordCountMap.getKey();
					RecordsCount recordsCounts = recordCountMap.getValue();
					list.add(recordsCounts);
					recordsMap.put(emailAddress, list);
				}
			} else {
				for (Entry<String, RecordsCount> recordCountMap : map2.entrySet()) {
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
		}
		sendRecodEmailDetailsnew(recordsMap);
	}

	@Override
	public EmailDetails getProviderDetails(String corporateTaxID, String providerTin, String uuID) {
		return emailRepository.getProviderDetails(corporateTaxID, providerTin, uuID);
	}

	public Map<String, RecordsCount> getCountForRecordOld(EmailDetails emailDetails) {

		Map<String, RecordsCount> mapCount = new HashMap<String, RecordsCount>();
		RecordsCount count2 = new RecordsCount();
		if (isValidType(emailDetails.getReconAlert())) {
			List<RecordDetails> recordDetails = emailRepository.getRecordList(emailDetails.getProviderTin(),
					Constants.RECORD_TYPE_RECON, Constants.FREQ_DAILY);
			count2.setPendFrequency(emailDetails.getPendFrequency());
			count2.setReconFrequency(emailDetails.getReconFrequency());
			count2.setCorporateTaxID(emailDetails.getCorporateTaxID());
			count2.setProviderTin(emailDetails.getProviderTin());
			count2.setReconCount(recordDetails.size());
			mapCount.put(emailDetails.getReconEmailAddress(), count2);
		}
		if (isValidType(emailDetails.getPendAlert())) {
			List<RecordDetails> recordDetails = emailRepository.getRecordList(emailDetails.getProviderTin(),
					Constants.RECORD_TYPE_PEND, Constants.FREQ_DAILY);
			if (mapCount.containsKey(emailDetails.getPendEmailAddress())) {
				RecordsCount coun = mapCount.get(emailDetails.getPendEmailAddress());
				coun.setPendFrequency(emailDetails.getPendFrequency());
				coun.setReconFrequency(emailDetails.getReconFrequency());
				coun.setCorporateTaxID(emailDetails.getCorporateTaxID());
				coun.setProviderTin(emailDetails.getProviderTin());
				coun.setPendCount(recordDetails.size());
			} else {
				RecordsCount count = new RecordsCount();
				count.setPendFrequency(emailDetails.getPendFrequency());
				count.setReconFrequency(emailDetails.getReconFrequency());
				count.setCorporateTaxID(emailDetails.getCorporateTaxID());
				count.setProviderTin(emailDetails.getProviderTin());
				count.setPendCount(1);
				mapCount.put(emailDetails.getPendEmailAddress(), count);

			}

		} 
		if (LocalDate.isMondayToday()) {
			if (isValidType(emailDetails.getReconAlert())) {
				List<RecordDetails> recordDetails = emailRepository.getRecordList(emailDetails.getProviderTin(),
						Constants.RECORD_TYPE_RECON, Constants.FREQ_WEEKLY);
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
			if (isValidType(emailDetails.getPendAlert())) {
				List<RecordDetails> recordDetails = emailRepository.getRecordList(emailDetails.getProviderTin(),
						Constants.RECORD_TYPE_PEND, Constants.FREQ_WEEKLY);
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
				sendMail(emailAddress, getHTMLMessage(recordsCounts, "Venkat"));
			} catch (Exception ex) {
				throw ex;
			}
		}
	}

	private String getHTMLMessage(List<RecordsCount> recordCountList, String name) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<!DOCTYPE html><html>");
		stringBuilder.append("<head></head><body> <p>Hello  ");
		if (null != name) {
			stringBuilder.append(name);
		} else {
			stringBuilder.append("Care Provider");
		}
		stringBuilder.append("</p> <p>");
		stringBuilder.append("<table >");
		stringBuilder.append("<tr>");
		stringBuilder.append(" <th>Corporate Tax ID</th>");
		stringBuilder.append("<th>Provider Tin</th>");
		stringBuilder.append(" <th>Recod Type</th>");
		stringBuilder.append(" <th>Recod Count</th>");
		stringBuilder.append(" <th>Frequency</th>");
		stringBuilder.append("</tr>");
		for (RecordsCount recordsCount : recordCountList) {

			if (recordsCount.getReconCount() > 0 && Constants.FREQ_DAILY.equals(recordsCount.getReconFrequency())) {
				stringBuilder.append("<tr>");
				stringBuilder.append(" <th>" + recordsCount.getCorporateTaxID() + "</th>");
				stringBuilder.append(" <th>" + recordsCount.getProviderTin() + "</th>");
				stringBuilder.append(" <th>" + Constants.RECORD_TYPE_RECON + "</th>");
				stringBuilder.append(" <th>" + recordsCount.getReconCount() + "</th>");
				stringBuilder.append(" <th>" + recordsCount.getReconFrequency() + "</th>");
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
				stringBuilder.append("</tr>");
			}

			if (recordsCount.getWeeklyPendCount() > 0
					&& Constants.FREQ_WEEKLY.equals(recordsCount.getPendFrequency())) {
				stringBuilder.append("</p> You have ");
				stringBuilder.append(recordsCount.getWeeklyPendCount());
				stringBuilder.append(" Pended tickets that have been update in last week,");
			}
		}
		stringBuilder.append("</table >");
		// }
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
}
