//package com.email.api.service;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import com.email.api.model.Notification;
//import com.email.api.model.SmartEdits;
//import com.email.api.utilities.LocalDate;
//
//@Service
//public class SmartEditsService {
//
//	public List<Notification> getSmartEditsNotificationList(String tinId) {
//		String url = "https://utils-stage.linkhealth.com/claims-api/api/claim/notifications?tin={tin}";
//		// create an instance of RestTemplate
//		RestTemplate restTemplate = new RestTemplate();
//		// make an HTTP GET request
//		SmartEdits smartEdits = (SmartEdits) restTemplate.getForObject(url, SmartEdits.class, tinId);
//		return getFilteredAceExpirationDate(smartEdits.getNotificationList());
//	}
//
//	public List<Notification> getFilteredAceExpirationDate(List<Notification> notificatonList) {
//		List<Notification> notifications = new ArrayList<>();
//		
//		if (notificatonList == null || notificatonList.isEmpty()) {
//			return notifications;
//		}
//
//		Date dailyDate = LocalDate.getLastUpdatedDate(1);
//
//		for (Notification notification : notificatonList) {
//			Date aceExpirationDate = LocalDate.getConvertedDate(notification.getAceExpirationDate());
//			if (dailyDate.before(aceExpirationDate)) {
//				notifications.add(notification);
//			}
//		}
//		return notifications;
//	}
//
//}
