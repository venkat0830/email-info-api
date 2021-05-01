//package com.email.api;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.mail.internet.MimeMessage;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.BDDMockito;
//import org.mockito.Mockito;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//import org.springframework.mail.javamail.JavaMailSender;
//
//import com.email.api.model.AuditEmailDetails;
//import com.email.api.model.EmailDetails;
//import com.email.api.repository.EmailRepositoryImpl;
//import com.email.api.service.EmailServiceImpl;
//import com.email.api.utilities.LocalDate;
//
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(LocalDate.class)
//public class TestEmailService {
//
//	EmailServiceImpl emailService;
//	EmailRepositoryImpl emailRepositoryImpl;
//	JavaMailSender javaMailSender;
//	String providerTin = "123456789";
//	String emailAdd = "venkatsai0830@gmail.com";
//	String corporateTaxID = "12345432134";
//
//	@Before
//	public void setup() throws Exception {
//		emailService = new EmailServiceImpl();
//		emailRepositoryImpl = PowerMockito.mock(EmailRepositoryImpl.class);
//		javaMailSender = PowerMockito.mock(JavaMailSender.class);
//		emailService.setEmailRepository(emailRepositoryImpl);
//		emailService.setJavaMailSender(javaMailSender);
// 
//	}
//
//	private List<EmailDetails> createEmailDetails() {
//		List<EmailDetails> list = new ArrayList<EmailDetails>();
//		list.add(createEmailReconDetails("Daily"));
//		list.add(createEmailReconDetails("Weekly"));
//		list.add(createEmailPendDetails("Daily"));
//		list.add(createEmailPendDetails("Weekly"));
//		return list;
//	}
//
//	@Test
//	public void sendDailyTest() throws Exception {
//		Mockito.when(emailRepositoryImpl.getDetailsWithPrimaryEmailAddress(emailAdd)).thenReturn(createEmailDetails());
//		Mockito.when(emailRepositoryImpl.getRecordList(providerTin, "RECON", "Daily")).thenReturn(2);
//		Mockito.when(emailRepositoryImpl.getRecordList(providerTin, "PEND", "Daily")).thenReturn(2);
//		Mockito.when(emailRepositoryImpl.getRecordList(providerTin, "RECON", "Weekly")).thenReturn(2);
//		Mockito.when(emailRepositoryImpl.getRecordList(providerTin, "PEND", "Weekly")).thenReturn(2);
//		MimeMessage message = Mockito.mock(MimeMessage.class);
//		Mockito.when(javaMailSender.createMimeMessage()).thenReturn(message);
//		Mockito.doNothing().when(javaMailSender).send(message);
//		emailService.sendDailyEmail(emailAdd);
//	}
//
//	@Test
//	public void sendWeeklyTest() throws Exception {
//		PowerMockito.mockStatic(LocalDate.class);
//		BDDMockito.given(LocalDate.isMondayToday()).willReturn(true);
//		Mockito.when(emailRepositoryImpl.getDetailsWithPrimaryEmailAddress(emailAdd)).thenReturn(createEmailDetails());
//		Mockito.when(emailRepositoryImpl.getRecordList(providerTin, "RECON", "Weekly")).thenReturn(2);
//		Mockito.when(emailRepositoryImpl.getRecordList(providerTin, "PEND", "Weekly")).thenReturn(2);
//		Mockito.when(emailRepositoryImpl.getRecordList(providerTin, "PEND", "Daily")).thenReturn(2);
//		MimeMessage message = Mockito.mock(MimeMessage.class);
//		Mockito.when(javaMailSender.createMimeMessage()).thenReturn(message);
//		Mockito.doNothing().when(javaMailSender).send(message);
//		emailService.sendDailyEmail(emailAdd);
//	}
//
//	private EmailDetails createEmailReconDetails(String freq) { 
//		EmailDetails details = new EmailDetails(); 
//		details.setReconAlert(true);
//		details.setPendAlert(true);
//		details.setProviderTin(providerTin);
//		details.setCorporateProviderMpin(corporateTaxID);
//		details.setReconFrequency(freq);
//		details.setPendFrequency(freq);
//		details.setUuID("UUID123");
//		details.setReconEmailAddress(emailAdd);
//		details.setPendEmailAddress(emailAdd);
//		details.setProviderName("providerName");
//		return details;
//	}
//
//	private EmailDetails createEmailPendDetails(String freq) {
//		EmailDetails details = new EmailDetails();
//		details.setReconAlert(true);
//		details.setPendAlert(true);
//		details.setProviderTin(providerTin);
//		details.setCorporateProviderMpin(corporateTaxID);
//		details.setReconFrequency(freq);
//		details.setPendFrequency(freq);
//		details.setUuID("UUID123");
//		details.setReconEmailAddress(emailAdd);
//		details.setPendEmailAddress(emailAdd);
//		details.setProviderName("providerName");
//		return details;
//	}
//
//	private AuditEmailDetails createAuditReport() {
//		AuditEmailDetails details = new AuditEmailDetails();
//		details.setCorporateTaxID(corporateTaxID);
//		details.setProviderTin(providerTin);
//		details.setReconCount(1);
//		details.setWeeklyReconCount(1);
//		details.setPendCount(1);
//		details.setWeeklyPendCount(1);
//		details.setReconFrequency("Daily");
//		details.setPendFrequency("Daily");
//		details.setEmailAddress(emailAdd);
//		details.setUuID("UUID123");
//		details.setCreatedDate(LocalDate.getCDT());
//		return details;
//
//	}
//
//	private List<AuditEmailDetails> createAudit() {
//		List<AuditEmailDetails> list = new ArrayList<AuditEmailDetails>();
//		list.add(createAuditReport());
//		return list;  
//	}
// 
//	@Test
//	public void getAuditDetailsTest() throws Exception {
//		Mockito.when(emailRepositoryImpl.getAuditEmailDetails(emailAdd, providerTin, "UUID123"))
//				.thenReturn(createAudit());
//		emailService.getAuditEmailDetails(emailAdd, providerTin, "UUID123");
//
//	}
//
//	@Test
//	public void getAuditSummaryTest() throws Exception {
//		Mockito.when(emailRepositoryImpl.getAuditEmailDetails(emailAdd, providerTin, "UUID123"))
//				.thenReturn(createAudit());
//		emailService.getAuditSummary(emailAdd, providerTin, "UUID123");
//
//	}
//}
