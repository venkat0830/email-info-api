package com.email.api.repository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.email.api.model.EmailDetails;
import com.email.api.model.RecordDetails;
import com.email.api.model.RecordInfo;
import com.email.api.utilities.Constants;
import com.email.api.utilities.LocalDate;
import com.email.api.utilities.LocalDtTime;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class RecordDaoImpl implements RecordDao {
	public Integer getRecordDetails(String providerTin, String recordType, String frequency) {
		List<Record> recordList = new ArrayList<E>();
		DBCollection recordsList = mongoTemplate.getCollection("recordCollection");
		if (recordType.equalsIgnoreCase(Constants.RECORD_TYPE_RECON)) {
			recordsList = mongoTemplate.getCollection("reconCollection");
		}
		if (recordType.equalsIgnoreCase(Constants.RECORD_TYPE_PEND)) {
			recordsList = mongoTemplate.getCollection("pendCollection");
		}
		if (recordType.equalsIgnoreCase("HPC")) {
			recordsList =  mongoTemplate.getCollection("hpcCollection");
		}
		BasicDBObject andQuery = new BasicDBObject();
//		BasicDBObject dateQuery = new BasicDBObject();
		List<BasicDBObject> queryList = new ArrayList<>();
//		if (!"HPC".equalsIgnoreCase(recordType)) {
//			dateQuery.append("$gte", LocalDtTime.getLastUpdatedDate(frequency));
//			if (Constants.FREQ_DAILY.equals(frequency)) {
//				dateQuery.append("$lte", LocalDtTime.getCurrentDate());
//
//			}
//			if (Constants.FREQ_WEEKLY.equals(frequency)) {
//				dateQuery.append("$gte", LocalDtTime.getThisWeekMonday());
//			}
//		}
		queryList.add(new BasicDBObject("providerDetails.providerTin", providerTin));
		queryList.add(new BasicDBObject("recordInfo.recordType", recordType));
//		queryList.add(new BasicDBObject("recordInfo.recordLastUpdatedDate", dateQuery));
		andQuery.put("$and", queryList);
		List<DBObject> result = recordsList.find(andQuery).toArray();
		System.out.println("Operated Query: " + andQuery);
		for (DBObject dbObject : result) {
			RecordDetails recordDetails = getConvertedRecord(dbObject, frequency);
			if (recordDetails != null) {
				recordList.add(recordDetails);
			}
		}
		return recordList.size();
	}
	private RecordDetails getConvertedRecord(DBObject dbObject, String frequency) {
		RecordDetails recordDetails = new RecordDetails();
		String recordType = ((DBObject) dbObject.get("recordInfo")).get("recordType").toString();
		String date = ((DBObject) dbObject.get("recordInfo")).get("recordLastUpdateDate").toString();
		
		Date oldDate = LocalDtTime.getLastUpdatedDate(frequency);
		Date conditionDate = new Date();
		Date featureDate = new Date();
		
		if (Constants.FREQ_DAILY.equals(frequency)) {
			featureDate = LocalDtTime.getCurrentDate();
		}
		if (Constants.FREQ_WEEKLY.equals(frequency)) {
			featureDate = LocalDtTime.getThisWeekMonday();
		}
		
		if ("RECON".equalsIgnoreCase(recordType)) {
			if (((DBObject) dbObject.get("recordInfo")).get("recordId").toString().toUpperCase().startsWith("PIQ")
					&& LocalDtTime.isValidUnixDate(date)) {
				conditionDate = LocalDtTime.convertToDateUnixDate(date);
			} else {
				conditionDate = LocalDtTime.convertDate(date);
			}
		} else if ("PEND".equalsIgnoreCase(recordType)) {
			conditionDate = LocalDtTime.convertDate(date);
		} else {
			return null;
		}
		if (conditionDate.after(oldDate) && conditionDate.before(featureDate) ) {
			return recordDetails;
		}
		return null;
	}
	
//	private List<DBObject> getRecentData(List<DBOBject> result, String recordType) {
//		Date date = new Date();
//		if ("RECON".equalsIgnoreCase(recordType)||"PEND".equalsIgnoreCase(recordType)) {
//			
//		}
//		if (Constants.FREQ_DAILY.equals(frequency)) {
//		dateQuery.append("$lte", LocalDtTime.getCurrentDate());
//
//	}
//	if (Constants.FREQ_WEEKLY.equals(frequency)) {
//		dateQuery.append("$lte", LocalDtTime.getThisWeekMonday());
//	}
//	}

	public List<EmailDetails> getEmailDetails(String frequency) {
	 List<EmailDetails> emailRecordsList = new ArrayList<>();
	 DBCollection emailCollection = mongoTemplate.getCollection(emailCollection);
	 BasicDBObject orQuery = new BasicDBObject();
	 List<BasicDBObject> queryList = new ArrayList<>();
	 DBCursor dbCursor;
	 if (frequency != null) {
	 queryList.add(new BasicDBObject(("reconFrequency") frequency));
	 queryList.add(new BasicDBObject(("pendFrequency") frequency));
	 orQuery.put("$or", queryList);
	 dbCursor = emailCollection.find(orQuery);
	 System.out.println("Operated Query:" + orQuery);
	 }
	 else {
//	 orQuery.put("$or", queryList);
	 dbCursor = emailCollection.find();
	 System.out.println("Operated Query:" + orQuery);
	 }
	 while (dbCursor.hasNext());
	 DBObject dbObject = dbCursor.next();
	 EmailDetails emailDetails =
	 mongoTemplate.getConverter().read(EmailDetails.class, dbObject);
	 emailRecordsList.add(emailDetails);
	 return emailRecordsList;
	 }

}
