package com.email.api.repository;

import java.util.Date;
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

	@Override
	public List<EmailDetails> getEmailDetails(String frequency) {
		Query query = new Query();
		query.addCriteria(Criteria.where("reconFrequency").is(frequency));
		return mongoTemplate.find(query, EmailDetails.class);
	}

	@Override
	public List<RecordDetails> getDocuments(String providerTin) {
		Query query = new Query();
		query.addCriteria(Criteria.where("providerDetails.providerTin").is(providerTin));
//		 String recoredLastUpdateDate = LocalDate.getLastUpdatedDate(1);
//		 query.addCriteria(Criteria.where("recordInfo.recordLastUpdateDate").gt(recoredLastUpdateDate));
		return mongoTemplate.find(query, RecordDetails.class);
	}

}
