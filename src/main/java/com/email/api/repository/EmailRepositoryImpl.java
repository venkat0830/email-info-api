package com.email.api.repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.email.api.model.AuditEmailDetails;
import com.email.api.model.EmailDetails;
import com.email.api.model.RecordDetails;
import com.email.api.utilities.LocalDate;

@Repository
public class EmailRepositoryImpl {

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	MongoOperations mongoOperations;

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public List<RecordDetails> getRecordList(String providerTin, String recordType, String frequency) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		Map<String, String> map = new HashMap<String, String>();
		map.put("RECON", "trackmyrecordRecon");
		map.put("PEND", "trackmyrecordPend");
		map.put("APPEALS", "trackmyrecordAppeals");
		map.put("SMARTEDIT", "trackmyrecordSmartEdit");
		map.put("PHARMACOUPON", "trackmyrecordPharmaCoupon");

		if (recordType.equals("RECON") || recordType.equals("PEND") || recordType.equals("APPEALS")
				|| recordType.equals("HPC")) {
			criteria.andOperator(new Criteria("providerDetails.providerTin").is(providerTin),
					new Criteria().orOperator(
							new Criteria().andOperator(
									Criteria.where("recordInfo.recordLastUpdateDate")
											.gte(LocalDate.getLastUpdatedDate(frequency, false)),
									Criteria.where("recordInfo.recordLastUpdateDate")
											.lte(LocalDate.getCurrentDay(frequency, false))),
							new Criteria().andOperator(
									Criteria.where("recordInfo.recordLastUpdateDate")
											.gte(LocalDate.getLastUpdatedDate(frequency, true)),
									Criteria.where("recordInfo.recordLastUpdateDate")
											.lte(LocalDate.getCurrentDay(frequency, true)))));
			query.addCriteria(criteria);
			System.out.println("Operated Query:" + query);
		}
		if (recordType.equals("PHARMACOUPON")) {
			criteria.andOperator(new Criteria("providerDetails.providerTin").is(providerTin),
					new Criteria("recordInfo.phrEmailSent").is(false),
					new Criteria().andOperator(
							Criteria.where("recordInfo.recordLastUpdateDate")
									.gte(LocalDate.getLastUpdatedDate(frequency, false)),
							Criteria.where("recordInfo.recordLastUpdateDate")
									.lte(LocalDate.getCurrentDay(frequency, false))));
			query.addCriteria(criteria);
			System.out.println("Operated Query for PHR:" + query);
			List<RecordDetails> phr = mongoTemplate.find(query, RecordDetails.class, map.get(recordType));
			System.out.println("Number of PharmaDetails :" + phr + "for providerTin:" + providerTin);
			return phr;
		}
		List<RecordDetails> result = mongoTemplate.findDistinct(query, "recordInfo.recordId", map.get(recordType),
				RecordDetails.class);
		return result;
	}

	public List<EmailDetails> getDetailsWithPrimaryEmailAddress(String primaryEmailAddress) {
		Query query = new Query();
		query.addCriteria(Criteria.where("primaryEmailAddress").is(primaryEmailAddress));
		System.out.println("Operated Query:" + query);
		List<EmailDetails> details = mongoTemplate.find(query, EmailDetails.class);
		return details;
	}

	public void saveAudit(AuditEmailDetails auditEmailDeteals) {

		mongoTemplate.save(auditEmailDeteals);
	}

	public List<AuditEmailDetails> getAuditEmailDetails(String emailAddress, String providerTin, String uuID) {
		Query query = new Query();
		Criteria criteria = new Criteria();

		criteria.andOperator(new Criteria("emailAddress").is(emailAddress), new Criteria("providerTin").is(providerTin),
				new Criteria("notificationDateAndTime").gte(LocalDateTime.now(ZoneId.of("America/Chicago"))
						.minusDays(10).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US))),
				new Criteria("notificationDateAndTime").lte(LocalDateTime.now(ZoneId.of("America/Chicago"))
						.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US))));
		query.addCriteria(criteria);
		System.out.println("Query : " + query);
		List<AuditEmailDetails> emailDetails = mongoTemplate.find(query, AuditEmailDetails.class);
		return emailDetails;
	}

	public void updatePhr(String providerTin, String frequency) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(new Criteria("providerDetails.providerTin").is(providerTin),
				new Criteria("recordInfo.phrEmailSent").is(false),
				new Criteria().andOperator(
						Criteria.where("recordInfo.recordLastUpdateDate")
								.gte(LocalDate.getLastUpdatedDate(frequency, false)),
						Criteria.where("recordInfo.recordLastUpdateDate")
								.lte(LocalDate.getCurrentDay(frequency, false))));
		query.addCriteria(criteria);
		Update update = new Update();
		update.set("recordInfo.phrEmailSent", true);

		mongoOperations.updateMulti(query, update, RecordDetails.class, "trackmyrecordPharmaCoupon");
	}
}
