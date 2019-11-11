package com.email.api.model;

import org.springframework.data.annotation.Transient;

import com.email.api.utilities.LocalDate;

public class RecordInfo {

	private String recordId;
	private String recordType;
	private String recordStatus;
	private String recordLastUpdateDate;

	public RecordInfo() {

	}

	public RecordInfo(String recordId, String recordType, String recordStatus, String recordLastUpdateDate) {
		super();
		this.recordId = recordId;
		this.recordType = recordType;
		this.recordStatus = recordStatus;
		this.recordLastUpdateDate = recordLastUpdateDate;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	public String getRecordLastUpdateDate() {
		return recordLastUpdateDate;
	}

	public void setRecordLastUpdateDate(String recordLastUpdateDate) {
		this.recordLastUpdateDate = recordLastUpdateDate;
	}

}
