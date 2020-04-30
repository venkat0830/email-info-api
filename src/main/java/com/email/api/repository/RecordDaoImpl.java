//package com.email.api.repository;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.email.api.model.EmailDetails;
//import com.email.api.model.RecordDetails;
//import com.email.api.model.RecordInfo;
//import com.email.api.utilities.Constants;
//import com.email.api.utilities.LocalDate;
//import com.email.api.utilities.LocalDtTime;
//import com.mongodb.BasicDBObject;
//import com.mongodb.DBCollection;
//import com.mongodb.DBCursor;
//import com.mongodb.DBObject;
//
//public class RecordDaoImpl implements RecordDao {
//	public Integer getRecordDetails(String providerTin, String recordType, String frequency) {
//		List<Record> recordList = new ArrayList<E>();
//		DBCollection recordsList = mongoTemplate.getCollection("recordCollection");
//		if (recordType.equalsIgnoreCase(Constants.RECORD_TYPE_RECON)) {
//			recordsList = mongoTemplate.getCollection("reconCollection");
//		}
//		if (recordType.equalsIgnoreCase(Constants.RECORD_TYPE_PEND)) {
//			recordsList = mongoTemplate.getCollection("pendCollection");
//		}
//		if (recordType.equalsIgnoreCase("HPC")) {
//			recordsList =  mongoTemplate.getCollection("hpcCollection");
//		}
//		BasicDBObject andQuery = new BasicDBObject();
//		BasicDBObject dateQuery = new BasicDBObject();
//		List<BasicDBObject> queryList = new ArrayList<>();
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
//		queryList.add(new BasicDBObject("providerDetails.providerTin", providerTin));
//		queryList.add(new BasicDBObject("recordInfo.recordType", recordType));
//		queryList.add(new BasicDBObject("recordInfo.recordLastUpdatedDate", dateQuery));
//		andQuery.put("$and", queryList);
//		List<DBObject> result = recordsList.find(andQuery).toArray();
//		System.out.println("Operated Query: " + andQuery);
//		for (DBObject dbObject : result) {
//			RecordDetails recordDetails = getConvertedRecord(dbObject);
//			recordsList.add(record);
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
//
//}
