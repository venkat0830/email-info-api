package com.email.api.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.email.api.model.EmailDetails;
import com.email.api.model.RecordDetails;
import com.email.api.model.RecordInfo;
import com.email.api.utilities.LocalDate;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

@Repository
public class EmailRepositoryImpl implements EmailRepository {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Value("${spring.data.mongodb.emailCollection}")
	private String emailCollection;

	@Override
	public List<EmailDetails> getEmailDetails(String frequency) {
		DBCollection emailDetails = (DBCollection) mongoTemplate.getCollection(this.emailCollection);
		BasicDBObject orQuery = new BasicDBObject();
		List<BasicDBObject> queryList = new ArrayList<>();
		queryList.add(new BasicDBObject("reconFrequency", frequency));
		queryList.add(new BasicDBObject("pendFrequency", frequency));
		orQuery.put("$or", queryList);
		System.out.println("Query Operated: " + orQuery);
		List<DBObject> result = emailDetails.find(orQuery).toArray();
		List<EmailDetails> emailRecord = new ArrayList<>();

		for (DBObject dbObject : result) {
			emailRecord.add(getConvertedEmailDetails(dbObject));
		}

		return emailRecord;
	}

	private EmailDetails getConvertedEmailDetails(DBObject dbObject) {
		EmailDetails emailDetails = new EmailDetails();
		emailDetails.setReconFrequency((String) dbObject.get("reconFrequency"));
		emailDetails.setPendFrequency((String) dbObject.get("pendFrequency"));
		emailDetails.setReconEmailAddress((String) dbObject.get("reconEmailAddress"));
		emailDetails.setPendEmailAddress((String) dbObject.get("pendEmailAddress"));
		emailDetails.setPendAlert((Boolean) dbObject.get("pendAlert"));
		emailDetails.setReconAlert((Boolean) dbObject.get("reconAlert"));
		emailDetails.setCorporateTaxID((String) dbObject.get("corporateTaxID"));
		emailDetails.setProviderTin((String) dbObject.get("providerTin"));
		emailDetails.setPrimaryEmailAddress((String) dbObject.get("primaryEmailAddress"));

		return emailDetails;
	}

	@Override
	public Integer getRecordList(String providerTin, String recordType, String frequency) {
		DBCollection recordsList = (DBCollection) mongoTemplate.getCollection("trackItRecords");
		BasicDBObject andQuery = new BasicDBObject();
		BasicDBObject date = new BasicDBObject();
		List<BasicDBObject> queryList = new ArrayList<>();
		date.append("$gte", LocalDate.getLastUpdatedDate(frequency));
		date.append("$lte", LocalDate.getCurrentDate());
		queryList.add(new BasicDBObject("providerDetails.providerTin", providerTin));
		queryList.add(new BasicDBObject("recordInfo.recordType", recordType));
		queryList.add(new BasicDBObject("recordInfo.recordLastUpdateDate", date));
		andQuery.put("$and", queryList);
		List<DBObject> result = recordsList.find(andQuery).toArray();
		List<RecordDetails> recordList = new ArrayList<>();
		for (DBObject dbObject : result) {
			RecordDetails record = getConvertedRecord(dbObject);
			recordList.add(record);
		}
		return getDateFilteredRecords(recordList, frequency).size();
	}

	private RecordDetails getConvertedRecord(DBObject dbObject) {
		RecordDetails record = new RecordDetails();
		RecordInfo recordInfo = (RecordInfo) dbObject.get("recordInfo");
		record.setRecordInfo(recordInfo);
		return record;
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

}
