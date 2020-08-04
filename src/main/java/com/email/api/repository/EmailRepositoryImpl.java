package com.email.api.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.email.api.model.EmailDetails;
import com.email.api.model.RecordDetails;
import com.email.api.utilities.LocalDate;

@Repository
public class EmailRepositoryImpl implements EmailRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	// private String emailCollection = "trackEmail";

	@Override
	public List<EmailDetails> getEmailDetails(String frequency) {

		Query query = new Query();
		Criteria criteria = new Criteria();
		if ("Daily".equals(frequency)) {
			criteria.orOperator(new Criteria("reconFrequency").is(frequency),
					new Criteria("pendFrequency").is(frequency));
			query.addCriteria(criteria);
		}
		System.out.println("Query: " + query);
		return mongoTemplate.find(query, EmailDetails.class);
	}

	@Override
	public List<RecordDetails> getRecordList(String providerTin, String recordType, String frequency) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(new Criteria("providerTin").is(providerTin),
				new Criteria("recordInfo.recordType").is(recordType),
				new Criteria("recordInfo.recordLastUpdateDate").gte(LocalDate.getLastUpdatedDate(frequency)),
				new Criteria("recordInfo.recordLastUpdateDate").lte(LocalDate.getCurrentDate(frequency)));
		query.addCriteria(criteria);
		System.out.println("operated Query:" + query);
		List<RecordDetails> result = mongoTemplate.find(query, RecordDetails.class);
		List<String> recordId = new ArrayList<String>();
		List<RecordDetails> finalList = new ArrayList<RecordDetails>();
		for (RecordDetails details : result) {
			if (!recordId.contains(details.getRecordInfo().getRecordId())) {
				recordId.add(details.getRecordInfo().getRecordId());
				finalList.add(details);
			}
		}
		System.out.println("Records Retrieved:" + recordId);
		return finalList;
	}

//	private List<RecordDetails> getDateFilteredRecords(List<RecordDetails> resultsRecords, String frequency) {
//		List<RecordDetails> fillterRecords = new ArrayList<>();
//		Date recoredLastUpdateDate = LocalDate.getLastUpdatedDate(frequency);
//		Date currentDate = LocalDate.getCurrentDate();
//		for (RecordDetails record : resultsRecords) {
//			if (recoredLastUpdateDate
//					.before(LocalDate.getFromatedDate(record.getRecordInfo().getRecordLastUpdateDate()))
//					&& currentDate.after(LocalDate.getFromatedDate(record.getRecordInfo().getRecordLastUpdateDate()))) {
//
//				fillterRecords.add(record);
//			}
//		}
//		return fillterRecords;
//	}

	@Override
	public EmailDetails getProviderDetails(String corporateTaxID, String providerTin, String uuID) {
		Query query = new Query();
		query.addCriteria(Criteria.where("corporateTaxID").is(corporateTaxID));
		query.addCriteria(Criteria.where("providerTin").is(providerTin));
		query.addCriteria(Criteria.where("uuID").is(uuID));
		System.out.println("Query : " + query);
		List<EmailDetails> emailDetails = mongoTemplate.find(query, EmailDetails.class);
		return emailDetails.get(0);
	}

	@Override
	public List<EmailDetails> getDetailsWithPrimaryEmailAddress(String primaryEmailAddress) {
		Query query = new Query();
		query.addCriteria(Criteria.where("primaryEmailAddress").is(primaryEmailAddress));
		System.out.println("Operated Query:" + query);
		List<EmailDetails> details = mongoTemplate.find(query, EmailDetails.class);
		return details;
	}

//	@Override
//	public void saveAudit(AuditEmailDetails auditEmailDeteals) {
//		
//		mongoTemplate.save(auditEmailDeteals);
//	}

//	@Override
//	public List<AuditEmailDetails> getAuditEmailDetails(String corporateTaxID, String providerTin, String uuID) {
//		Query query = new Query();
//		query.addCriteria(Criteria.where("corporateTaxID").is(corporateTaxID));
//		query.addCriteria(Criteria.where("providerTin").is(providerTin));
//		query.addCriteria(Criteria.where("uuID").is(uuID));
//		System.out.println("Query : " + query);
//		List<AuditEmailDeteals> emailDetails = mongoTemplate.find(query, AuditEmailDeteals.class);
//		return emailDetails;
//	}

}
