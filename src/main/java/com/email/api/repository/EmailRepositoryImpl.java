package com.email.api.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.email.api.model.EmailDetails;
import com.email.api.model.RecordDetails;
import com.email.api.model.RecordInfo;
import com.email.api.utilities.Constants;
import com.email.api.utilities.LocalDate;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Repository
public class EmailRepositoryImpl implements EmailRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	// private String emailCollection = "trackEmail";

	@Override
	public List<EmailDetails> getEmailDetails(String frequency) {

		Query query = new Query();
		Criteria criteria = new Criteria();
		if (frequency != null) {
			criteria.orOperator(new Criteria("reconFrequency").is(frequency),
					new Criteria("pendFrequency").is(frequency));
			query.addCriteria(criteria);
		}
		System.out.println("Query: " + query);
		return mongoTemplate.find(query, EmailDetails.class);
	}

	@Override
	public List<RecordDetails> getRecordList(String providerTin,String recordType,  String frequency) {
		Query query = new Query();
		query.addCriteria(Criteria.where("providerDetails.providerTin").is(providerTin));
		query.addCriteria(Criteria.where("recordInfo.recordType").is(recordType));
		System.out.println("Query : " + query);
		List<RecordDetails> resultsRecords = mongoTemplate.find(query, RecordDetails.class);
		return getDateFilteredRecords(resultsRecords, frequency);
	}

	private List<RecordDetails> getDateFilteredRecords(List<RecordDetails> resultsRecords, String frequency) {
		List<RecordDetails> fillterRecords = new ArrayList<>();
		Date recoredLastUpdateDate = LocalDate.getLastUpdatedDate(frequency);
		Date currentDate = LocalDate.getCurrentDate();
		for (RecordDetails record : resultsRecords) {
			if (recoredLastUpdateDate
					.before(LocalDate.getFromatedDate(record.getRecordInfo().getRecordLastUpdateDate()))
					&& currentDate.after(LocalDate.getFromatedDate(record.getRecordInfo().getRecordLastUpdateDate()))) {

				fillterRecords.add(record);
			}
		}
		return fillterRecords;
	}

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

}
