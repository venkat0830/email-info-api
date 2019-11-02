package com.email.api.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.email.api.model.EmailDetails;
import com.email.api.model.RecordDetails;


@Repository
public class EmailRepositoryImpl  implements EmailRepository {
	

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public List<EmailDetails> getEmailDetails(String frequency) {
		Query query = new Query();
		query.addCriteria(Criteria.where("reconFrequency").is(frequency));
		return mongoTemplate.find(query, EmailDetails.class);
	}

	@Override
	public RecordDetails getDocuments(String providerTin) {
		Query query = new Query();
		query.addCriteria(Criteria.where("providerDetails.providerTin").is(providerTin));
		query.addCriteria(Criteria.where("recordInfo.recordType").is("RECON"));
		return mongoTemplate.findOne(query, RecordDetails.class);
	}
	
}
