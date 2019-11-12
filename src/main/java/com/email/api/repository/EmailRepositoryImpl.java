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
import com.email.api.utilities.Constants;
import com.email.api.utilities.LocalDate;

@Repository
public class EmailRepositoryImpl implements EmailRepository {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public List<EmailDetails> getEmailDetails(String frequency) {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.orOperator(new Criteria("reconFrequency").is(frequency), new Criteria("pendFrequency").is(frequency));
		query.addCriteria(criteria);
		return mongoTemplate.find(query, EmailDetails.class);
	}

	@Override
	public List<RecordDetails> getRecordList(String providerTin, String recordType, String frequency) {
		Query query = new Query();
		query.addCriteria(Criteria.where("providerDetails.providerTin").is(providerTin));
		query.addCriteria(Criteria.where("recordInfo.recordType").is(recordType));
		List<RecordDetails> resultsRecords = mongoTemplate.find(query, RecordDetails.class);
		return getDateFilteredRecords(resultsRecords, frequency);
	}

	private List<RecordDetails> getDateFilteredRecords(List<RecordDetails> resultsRecords, String frequency) {
		List<RecordDetails> fillterRecords = new ArrayList<>();
		Date recoredLastUpdateDate = new Date();
		if (frequency.equals(Constants.FREQ_DAILY)) {

			
			recoredLastUpdateDate = LocalDate.getLastUpdatedDate(1);
		}
		if (frequency.equals(Constants.FREQ_WEEKLY)) {
			recoredLastUpdateDate = LocalDate.getLastUpdatedDate(7);
		}
		for (RecordDetails record : resultsRecords) {
			if (recoredLastUpdateDate
					.after(LocalDate.getFromatedDate(record.getRecordInfo().getRecordLastUpdateDate()))) {
				fillterRecords.add(record);
			}
		}
		return fillterRecords;
	}

	
}
