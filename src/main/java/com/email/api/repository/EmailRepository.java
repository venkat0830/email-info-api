package com.email.api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.email.api.model.EmailDetails;

public interface EmailRepository extends MongoRepository<EmailDetails, String> {

	List<EmailDetails> findByPrimaryEmailAddress(String primaryEmailAddress);

}
