//package com.email.api;
//
//import java.time.LocalDateTime;
//import org.springframework.data.mongodb.core.query.Query;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.email.api.model.AuditEmailDetails;
//import com.email.api.model.ClaimDetails;
//import com.email.api.model.EmailDetails;
//import com.email.api.model.MemberDetails;
//import com.email.api.model.ProviderDetails;
//import com.email.api.model.RecordDetails;
//import com.email.api.model.RecordInfo;
//import com.email.api.repository.EmailRepositoryImpl;
//import com.email.api.utilities.LocalDate;
//
////@ContextConfiguration(classes = { EmailNotifyApiApplication.class })
//@RunWith(SpringRunner.class)
//public class TestEmailRepository {
//
////	@Autowired
//	EmailRepositoryImpl emailRepositoryImpl;
//	@Mock
//	MongoTemplate mongoTemplate;
//
//	LocalDate LocalDate;
//	String date1 = "2020-12-06 06:00:00";
//	String date2 = "2020-12-07 06:00:00";
//	String date3 = "1602780587320";
//
//	@Before
//	public void setup() throws Exception {
//		emailRepositoryImpl = new EmailRepositoryImpl();
//		emailRepositoryImpl.setMongoTemplate(mongoTemplate);
//
//	}
//
//	@Test
//	public void testAudit() {
//		Mockito.when(mongoTemplate.save(createAudit(), "testCollection")).thenReturn(createAudit());
//		emailRepositoryImpl.saveAudit(createAudit());
//	}
//
//	@Test
//	public void testGetDetailsWithPriamryEmailAddress() {
//		EmailDetails details = createEmailReconDetails("Daily");
//		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
//		query.addCriteria(Criteria.where("primaryEmailAddress").is(details.getReconEmailAddress()));
//		System.out.println("Operated Query:" + query);
//		Mockito.when(mongoTemplate.find(query, EmailDetails.class)).thenReturn(createEmaildetails());
//		emailRepositoryImpl.getDetailsWithPrimaryEmailAddress(details.getReconEmailAddress());
//
//	}
//
//	@Test
//	public void testRecordslist() {
//
//		Query query = new Query();
//		Criteria criteria = new Criteria();
//
//		criteria.andOperator(new Criteria("providerDetails.providerTin").is("123456789"), new Criteria().orOperator(
//				new Criteria().andOperator(
//						Criteria.where("recordInfo.recordLastUpdateDate")
//								.gte(com.email.api.utilities.LocalDate.getLastUpdatedDate("Daily", false)),
//						Criteria.where("recordInfo.recordLastUpdateDate").lte(com.email.api.utilities.LocalDate.getCurrentDay("Daily", false))),
//				new Criteria().andOperator(
//						Criteria.where("recordInfo.recordLastUpdateDate")
//								.gte(com.email.api.utilities.LocalDate.getLastUpdatedDate("Daily", true)),
//						Criteria.where("recordInfo.recordLastUpdateDate")
//								.lte(com.email.api.utilities.LocalDate.getCurrentDay("Daily", true)))));
//		query.addCriteria(criteria);
//		Mockito.when(mongoTemplate.findDistinct(query, "recordInfo.recordID", "trackItRecords", RecordDetails.class))
//				.thenReturn(getRecords());
//		emailRepositoryImpl.getRecordList("123456789", "RECON", "Daily"); 
//	}
//	
//	@Test
//	public void testRecordslist2() {
//
//		Query query = new Query();
//		Criteria criteria = new Criteria();
//
//		criteria.andOperator(new Criteria("providerDetails.providerTin").is("123456789"), new Criteria().orOperator(
//				new Criteria().andOperator(
//						Criteria.where("recordInfo.recordLastUpdateDate")
//								.gte(com.email.api.utilities.LocalDate.getLastUpdatedDate("Weekly", false)),
//						Criteria.where("recordInfo.recordLastUpdateDate").lte(com.email.api.utilities.LocalDate.getCurrentDay("Weekly", false))),
//				new Criteria().andOperator(
//						Criteria.where("recordInfo.recordLastUpdateDate")
//								.gte(com.email.api.utilities.LocalDate.getLastUpdatedDate("Weekly", true)),
//						Criteria.where("recordInfo.recordLastUpdateDate")
//								.lte(com.email.api.utilities.LocalDate.getCurrentDay("Weekly", true)))));
//		query.addCriteria(criteria);
//		Mockito.when(mongoTemplate.findDistinct(query, "recordInfo.recordID", "trackItRecords", RecordDetails.class))
//				.thenReturn(getRecords());
//		emailRepositoryImpl.getRecordList("123456789", "RECON", "Daily");
//	}
//
//
//	@Test
//	public void testAuditEmailDetails() {
//		AuditEmailDetails auditEmailDetails = createAudit();
//
//		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
//		Criteria criteria = new Criteria();
//
//		criteria.andOperator(new Criteria("emailAddress").is("emailAddress"),
//				new Criteria("providerTin").is("providerTin"),
//				new Criteria("notificationDateAndTime").gte(LocalDateTime.now(ZoneId.of("America/Chicago"))
//						.minusDays(10).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US))),
//				new Criteria("notificationDateAndTime").lte(LocalDateTime.now(ZoneId.of("America/Chicago"))
//						.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US))));
//		query.addCriteria(criteria);
//		System.out.println("Query : " + query);
//		Mockito.when(mongoTemplate.find(query, AuditEmailDetails.class)).thenReturn(getAuditDetails());
//		emailRepositoryImpl.getAuditEmailDetails(auditEmailDetails.getEmailAddress(),
//				auditEmailDetails.getProviderTin(), auditEmailDetails.getUuID());
//	}
//
//	@Test
//	public void testGetProviderDetails() {
//		EmailDetails details = createEmailReconDetails("Daily");
//		Query query = new Query();
//		query.addCriteria(Criteria.where("corporateTaxID").is(details.getCorporateProviderMpin()));
//		query.addCriteria(Criteria.where("providerTin").is(details.getProviderTin()));
//		query.addCriteria(Criteria.where("uuID").is(details.getUuID()));
//		System.out.println("Query : " + query);
//		Mockito.when(mongoTemplate.find(query, EmailDetails.class)).thenReturn(createEmaildetails());
//		emailRepositoryImpl.getProviderDetails(details.getCorporateProviderMpin(), details.getProviderTin(),
//				details.getUuID()); 
//	}
//
//	private AuditEmailDetails createAudit() {
//		AuditEmailDetails details = new AuditEmailDetails();
//		details.setCorporateTaxID("987654321");
//		details.setProviderTin("123456789");
//		details.setReconCount(1);
//		details.setWeeklyReconCount(1);
//		details.setPendCount(1);
//		details.setWeeklyPendCount(1);
//		details.setReconFrequency("Daily");
//		details.setPendFrequency("Daily");
//		details.setEmailAddress("test@gmail.com");
//		details.setUuID("UUID123");
//		details.setCreatedDate("2020-10-16 08:00:00");
//		return details;
//	}
//
//	private List<AuditEmailDetails> getAuditDetails() {
//		List<AuditEmailDetails> details = new ArrayList<AuditEmailDetails>();
//		details.add(createAudit());
//		return details;
//	}
//
//	private RecordDetails createRecords() {
//		RecordDetails details = new RecordDetails();
//		RecordInfo recordInfo = new RecordInfo();
//		ProviderDetails providerDetails = new ProviderDetails();
//		MemberDetails memberDetails = new MemberDetails();
//		ClaimDetails claimDetails = new ClaimDetails();
//		recordInfo.setRecordId("PIQ123");
//		recordInfo.setRecordLastUpdateDate("2020-10-10 06:00:00");
//		recordInfo.setRecordType("RECON");
//		providerDetails.setProviderTin("123456789");
//		providerDetails.setProviderName("Care provider");
//		memberDetails.setMemberId("13242");
//		claimDetails.setClaimNumber("2538672");
//		details.setRecordInfo(recordInfo);
//		details.setProviderDetails(providerDetails);
//		details.setMemberDetails(memberDetails);
//		details.setClaimDetails(claimDetails);
//		return details;
//	}
//
//	private List<RecordDetails> getRecords() {
//		List<RecordDetails> details = new ArrayList<RecordDetails>();
//		details.add(createRecords());
//		return details;
//
//	}
//
//	private List<EmailDetails> createEmaildetails() {
//		List<EmailDetails> details = new ArrayList<EmailDetails>();
//		details.add(createEmailReconDetails("Daily"));
//		details.add(createEmailReconDetails("Weekly"));
//		return details;
//	}
//
//	private EmailDetails createEmailReconDetails(String freq) {
//		EmailDetails details = new EmailDetails();
//		details.setReconAlert(true);
//		details.setReconEmailAddress("test123@gmail.com");
//		details.setReconFrequency(freq);
//		details.setProviderName("ProviderName");
//		details.setProviderTin("123456789");
//		details.setCorporateProviderMpin("692738768");
//		details.setUuID("UU74628364");
//		return details;
//
//	}
//
//}
