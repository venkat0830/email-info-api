package com.email.api.repository;

import java.util.List;

import com.email.api.model.EmailDetails;
import com.email.api.model.RecordDetails;

public interface EmailRepository {

	List<EmailDetails> getEmailDetails(String frequency);

	Integer getRecordList(String providerTin, String recordType, String frequency);
}
