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
					new Criteria("pendFrequency").is(frequency), new Criteria("smartEditsFrequency").is(frequency));
			query.addCriteria(criteria);
		}
		System.out.println("Query: " + query);
		return mongoTemplate.find(query, EmailDetails.class);
	}

	@Override
	public List<RecordDetails> getRecordList(String providerTin, String recordType, String frequency) {
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

//	public Integer getRecordDetails(String providerTin, String recordType, String frequency) {
//		DBCollection recordsList = mongoTemplate.getCollection("recordCollection");
//		if (recordType.equalsIgnoreCase(Constants.RECORD_TYPE_RECON)) {
//			recordsList = mongoTemplate.getCollection("reconCollection");
//		}
//		if (recordType.equalsIgnoreCase(Constants.RECORD_TYPE_PEND)) {
//			recordsList = mongoTemplate.getCollection("pendCollection");
//		}
//		BasicDBObject andQuery = new BasicDBObject();
//		BasicDBObject dateQuery = new BasicDBObject();
//		List<BasicDBObject> queryList = new ArrayList<>();
//		dateQuery.append("$gte", LocalDate.getLastUpdatedDate(frequency));
//		if (Constants.FREQ_DAILY.equals(frequency)) {
//			dateQuery.append("$lte", LocalDate.getCurrentDate());
//
//		}
//		if (Constants.FREQ_WEEKLY.equals(frequency)) {
//			dateQuery.append("$gte", LocalDate.getThisWeekMonday());
//		}
//		queryList.add(new BasicDBObject("providerDetails.providerTin", providerTin));
//		queryList.add(new BasicDBObject("recordInfo.recordType", recordType));
//		queryList.add(new BasicDBObject("recordInfo.recordLastUpdatedDate", dateQuery));
//		andQuery.put("$and", queryList);
//		List<DBObject> result = recordsList.find(andQuery).toArray();
//		System.out.println("Operated Query: " + andQuery);
//		for (DBObject dbObject : result) {
//			RecordDetails recordDetails = getConvertedRecord(dbObject);
//			recordsList.addOption(record);
//		}
//		return recordList.size();
//	}
//
//	private RecordDetails getConvertedRecord(DBObject dbObject) {
//		RecordDetails recordDetails = new RecordDetails();
//		RecordInfo recordInfo = mongoTemplate.getConverter().read(RecordInfo.class,
//				(DBObject) dbObject.get("recordInfo"));
//	}
//
//	public List<EmailDetails> getEmailDetails(String frequency) {
//	 List<EmailDetails> emailRecordsList = new ArrayList<>();
//	 DBCollection emailCollection = mongoTemplate.getCollection(emailCollection);
//	 BasicDBObject orQuery = new BasicDBObject();
//	 List<BasicDBObject> queryList = new ArrayList<>();
//	 DBCursor dbCursor;
//	 if (frequency != null) {
//	 queryList.add(new BasicDBObject(("reconFrequency") frequency));
//	 queryList.add(new BasicDBObject(("pendFrequency") frequency));
//	 orQuery.put("$or", queryList);
//	 dbCursor = emailCollection.find(orQuery);
//	 System.out.println("Operated Query:" + orQuery);
//	 }
//	 else {
////	 orQuery.put("$or", queryList);
//	 dbCursor = emailCollection.find();
//	 System.out.println("Operated Query:" + orQuery);
//	 }
//	 while (dbCursor.hasNext());
//	 DBObject dbObject = dbCursor.next();
//	 EmailDetails emailDetails =
//	 mongoTemplate.getConverter().read(EmailDetails.class, dbObject);
//	 emailRecordsList.add(emailDetails);
//	 return emailRecordsList;
//	 }

}
